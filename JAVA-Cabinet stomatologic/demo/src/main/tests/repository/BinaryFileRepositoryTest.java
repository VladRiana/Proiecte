package repository;

import domain.AbstractEntity;
import exceptions.RepositoryException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.*;

class BinaryFileRepositoryTest {
    private String testFilePath;
    private BinaryFileRepository<TestEntity> repository;

    @BeforeEach
    void setUp() throws RepositoryException {
        testFilePath = "C:\\Users\\Asus\\IdeaProjects\\a3-VladRiana\\test_repository.bin";
        repository = new BinaryFileRepository<>(testFilePath);
    }

    @AfterEach
    void tearDown() {
        File file = new File(testFilePath);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void loadData() throws RepositoryException {
        TestEntity entity = new TestEntity(1, "Test");
        repository.add(entity);
        repository = new BinaryFileRepository<>(testFilePath);
        TestEntity loadedEntity = repository.findById(1);
        assertNotNull(loadedEntity);
        assertEquals("Test", loadedEntity.getName());
    }

    @Test
    void saveData() throws RepositoryException {
        TestEntity entity = new TestEntity(2, "Test");
        repository.add(entity);
        repository.saveData();
        File file = new File(testFilePath);
        assertTrue(file.exists(), "Fisierul nu a fost creat.");
    }

    @Test
    void add() throws RepositoryException {
        TestEntity entity = new TestEntity(3, "Test");
        repository.add(entity);
        TestEntity foundEntity = repository.findById(3);
        assertNotNull(foundEntity, "Entitatea nu a fost adaugata.");
        assertEquals("Test", foundEntity.getName(), "Numele entitatii este incorect.");
    }

    @Test
    void remove() throws RepositoryException {
        TestEntity entity = new TestEntity(4, "Test");
        repository.add(entity);
        repository.remove(4);
        TestEntity foundEntity = repository.findById(4);
        assertNull(foundEntity, "Entitatea nu a fost stearsa.");
    }

    @Test
    void update() throws RepositoryException {
        TestEntity entity = new TestEntity(5, "Test");
        repository.add(entity);
        entity.setName("Actualizat");
        repository.update(entity);
        TestEntity updatedEntity = repository.findById(5);
        assertNotNull(updatedEntity, "Entitatea nu a fost actualizata.");
        assertEquals("Actualizat", updatedEntity.getName(), "Numele entitatii nu a fost actualizat.");
    }

    @Test
    void findById() throws RepositoryException {
        TestEntity entity = new TestEntity(6, "Test");
        repository.add(entity);
        TestEntity foundEntity = repository.findById(6);
        assertNotNull(foundEntity, "Entitatea nu a fost gasita.");
        assertEquals("Test", foundEntity.getName(), "Numele entitatii este incorect.");
    }

    static class TestEntity extends AbstractEntity implements Serializable {
        private String name;

        public TestEntity(int id, String name) {
            super(id);
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}