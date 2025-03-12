package repository;

import domain.AbstractEntity;
import exceptions.DuplicateIDException;
import exceptions.ObjectNotFoundException;
import exceptions.RepositoryException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Repository<T extends AbstractEntity> implements IRepository<T> {
    List<T> items = new ArrayList<>();

    @Override
    public void add(T item) throws RepositoryException {
        if(findById(item.getId()) != null) {
            throw new DuplicateIDException("Exista deja un element cu acest id!");
        }
        items.add(item);
    }

    @Override
    public void remove(int id) throws RepositoryException {
        T itemToRemove = findById(id);
        if(itemToRemove == null) {
            throw new ObjectNotFoundException("Nu exista un element cu id-ul dat!");
        }
        items.remove(itemToRemove);
    }

    @Override
    public void update(T item) throws RepositoryException {
        T itemToUpdate = findById(item.getId());
        if(itemToUpdate == null) {
            throw new ObjectNotFoundException("Nu exista un element cu id-ul dat!");
        }
        remove(itemToUpdate.getId());
        add(item);
    }

    @Override
    public T findById(int id) {
        for (T item : items) {
            if (id == item.getId()) {
                return item;
            }
        }
        return null;
    }

    @Override
    public Collection<T> getAll() {
        return new ArrayList<>(items);
    }

}
