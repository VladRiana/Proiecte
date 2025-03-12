package exceptions;

public class DuplicateIDException extends RuntimeException {

    public DuplicateIDException(String message) {
        super(message);
    }
}
