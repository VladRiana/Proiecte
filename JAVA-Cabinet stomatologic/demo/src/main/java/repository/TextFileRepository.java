package repository;

import domain.AbstractEntity;
import domain.AbstractEntityConverter;
import exceptions.RepositoryException;

import java.io.*;
import java.util.ArrayList;

public class TextFileRepository<T extends AbstractEntity> extends Repository<T> {
    private String fileName;
    protected AbstractEntityConverter<T> converter;

    public TextFileRepository(String fileName, AbstractEntityConverter<T> converter) throws RepositoryException {
        this.fileName = fileName;
        this.converter = converter;
        try {
            loadData();
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
    }

    protected void loadData() throws RepositoryException {
        File file = new File(fileName);

        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RepositoryException("Eroare la crearea fisierului: " + e.getMessage(), e);
            }
        }

        if (!file.canRead()) {
            throw new RepositoryException("Fisierul exista dar nu poate fi citit: " + fileName);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();
            while (line != null) {
                T item = converter.fromString(line);
                items.add(item);
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fisierul nu a fost gasit. Se initializeaza.");
            this.items = new ArrayList<>();
        } catch (EOFException e) {
            System.out.println("Fisierul este gol. Se initializeaza.");
            this.items = new ArrayList<>();
        } catch (IOException e) {
            throw new RepositoryException("Eroare la citirea fisierului: " + e.getMessage(), e);
        }
    }

    protected void saveData() throws RepositoryException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (T item : items) {
                String asString = converter.toString(item);
                bw.write(asString);
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RepositoryException("Eroare la scrierea fisierului: " + e.getMessage(), e);
        }
    }

    @Override
    public void add(T item) throws RepositoryException {
        super.add(item);
        saveData();
    }

    @Override
    public void remove(int id) throws RepositoryException {
        super.remove(id);
        saveData();
    }

    @Override
    public void update(T item) throws RepositoryException {
        super.update(item);
        saveData();
    }
}
