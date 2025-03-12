package domain;

public abstract class AbstractEntityConverter<T extends AbstractEntity> {
    public abstract String toString(T entity);
    public abstract T fromString(String entity);
}
