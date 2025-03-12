package repository;

import domain.AbstractEntity;
import exceptions.RepositoryException;

import java.io.*;
import java.util.ArrayList;

public class BinaryFileRepository<T extends AbstractEntity> extends Repository<T> {
    private String fileName;

    public BinaryFileRepository(String fileName) throws RepositoryException {
        this.fileName = fileName;
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

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            this.items = (ArrayList<T>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Fisierul nu a fost gasit. Se initializeaza.");
            this.items = new ArrayList<>();
        } catch (EOFException e) {
            System.out.println("Fisierul este gol. Se initializeaza.");
            this.items = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new RepositoryException("Eroare la incarcarea fisierului: " + e.getMessage(), e);
        }

    }

    protected void saveData() throws RepositoryException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(items);
        } catch (IOException e) {
            throw new RepositoryException("Eroare la salvarea fisierului: " + e.getMessage(), e);
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

    public T findById(int id) {
        return super.findById(id);
    }

}
