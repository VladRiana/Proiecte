package repository;

import domain.AbstractEntity;
import exceptions.DuplicateIDException;
import exceptions.ObjectNotFoundException;
import exceptions.RepositoryException;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryTest {

    @Test
    void add() throws RepositoryException {
        Repository<AbstractEntity> repository = new Repository<>();

        AbstractEntity entity1 = new AbstractEntity(1) {};
        repository.add(entity1);

        assertEquals(1, repository.getAll().size(), "Elementul nu a fost adaugat corect.");
        assertEquals(entity1, repository.findById(1), "Elementul adaugat nu este gasit.");

        AbstractEntity entity2 = new AbstractEntity(1) {};

        try {
            repository.add(entity2);
            fail("Ar trebui sa fie aruncata exceptia pentru ID duplicat.");
        } catch (DuplicateIDException e) {
            assertEquals("Exista deja un element cu acest id!", e.getMessage());
        }
    }

    @Test
    void remove() throws RepositoryException {
        Repository<AbstractEntity> repository = new Repository<>();

        AbstractEntity entity = new AbstractEntity(1) {};
        repository.add(entity);
        repository.remove(1);

        assertNull(repository.findById(1), "Elementul nu a fost sters corect.");
        assertEquals(0, repository.getAll().size(), "Lista nu este goala după stergere.");

        try {
            repository.remove(1);
            fail("Ar trebui sa fie aruncata exceptia ca nu exista un element cu acest ID.");
        } catch (ObjectNotFoundException e) {
            assertEquals("Nu exista un element cu id-ul dat!", e.getMessage());
        }
    }


    @Test
    void update() throws RepositoryException {
        Repository<AbstractEntity> repository = new Repository<>();

        AbstractEntity entity = new AbstractEntity(1) {};

        try {
            repository.update(entity);
            fail("Ar trebui sa fie aruncata excepția ca nu exista un element cu acest ID.");
        } catch (ObjectNotFoundException e) {
            assertEquals("Nu exista un element cu id-ul dat!", e.getMessage());
        }

        repository.add(entity);

        AbstractEntity updatedEntity = new AbstractEntity(1) {};
        repository.update(updatedEntity);

        assertEquals(updatedEntity, repository.findById(1), "Elementul nu a fost actualizat corect.");
    }

    @Test
    void findById() throws RepositoryException {
        Repository<AbstractEntity> repository = new Repository<>();

        AbstractEntity entity = new AbstractEntity(1) {};
        repository.add(entity);

        AbstractEntity result = repository.findById(1);
        assertEquals(entity, result, "Elementul nu a fost gasit corect.");
        assertNull(repository.findById(2), "Ar trebui sa returneze null pentru un ID inexistent.");
    }

    @Test
    void getAll() throws RepositoryException {
        Repository<AbstractEntity> repository = new Repository<>();

        Collection<AbstractEntity> allItems1 = repository.getAll();
        assertTrue(allItems1.isEmpty(), "Colectia ar trebui sa fie goala.");

        AbstractEntity entity1 = new AbstractEntity(1) {};
        AbstractEntity entity2 = new AbstractEntity(2) {};

        repository.add(entity1);
        repository.add(entity2);

        Collection<AbstractEntity> allItems = repository.getAll();
        assertEquals(2, allItems.size(), "Numarul elementelor returnate nu este corect.");
        assertTrue(allItems.contains(entity1), "Colectia nu conține elementul 1.");
        assertTrue(allItems.contains(entity2), "Colectia nu conține elementul 2.");
    }
}