package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractEntityTest {

    @Test
    void testConstructorImplicit() {
        AbstractEntity entity = new AbstractEntity() {};
        assertEquals(0, entity.getId(), "ID-ul implicit nu este corect.");
    }

    @Test
    void getId() {
        AbstractEntity entity = new AbstractEntity(10) {};
        assertEquals(10, entity.getId(), "ID-ul nu este corect returnat!");
    }

    @Test
    void setId() {
        AbstractEntity entity = new AbstractEntity(5) {};
        entity.setId(10);
        assertEquals(10, entity.getId(), "ID-ul nu este corect initializat!");
    }
}