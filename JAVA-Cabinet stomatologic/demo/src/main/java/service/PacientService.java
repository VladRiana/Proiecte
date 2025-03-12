package service;

import domain.Pacient;
import exceptions.ObjectNotFoundException;
import exceptions.RepositoryException;
import repository.Repository;

import java.util.Collection;

public class PacientService {
    private Repository<Pacient> pacientRepository;

    public PacientService(Repository<Pacient> pacientRepository) {
        this.pacientRepository = pacientRepository;
    }

    public void addPacient(Pacient pacient) throws RepositoryException {
        pacientRepository.add(pacient);
    }

    public void removePacient(int id) throws RepositoryException {
        pacientRepository.remove(id);
    }

    public void updatePacient(Pacient pacient) throws RepositoryException {
        pacientRepository.update(pacient);
    }

    public Pacient getPacientById(int id) throws RepositoryException {
        Pacient pacient = pacientRepository.findById(id);
        if (pacient == null) {
            throw new ObjectNotFoundException("Nu exista niciun pacient cu acest id!");
        }
        return pacient;
    }

    public Collection<Pacient> getAllPacienti() {
        Collection<Pacient> pacienti =  pacientRepository.getAll();
        if(pacienti.isEmpty()) {
            System.out.println("Nu exista niciun pacient!");
        }
        return pacienti;
    }
}
