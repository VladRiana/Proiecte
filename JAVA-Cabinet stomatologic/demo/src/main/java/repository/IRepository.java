package repository;

import domain.AbstractEntity;
import exceptions.RepositoryException;

import java.io.Serializable;
import java.util.Collection;

public interface IRepository<T extends AbstractEntity> extends Serializable {
    void add(T item) throws RepositoryException;
    void remove(int id) throws RepositoryException;
    void update(T item) throws RepositoryException;
    T findById(int id) throws RepositoryException;
    Collection<T> getAll();
}
