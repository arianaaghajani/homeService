package ir.ariana.hw19spring.exception;

public class WrongDateInsertException extends RuntimeException {
    public WrongDateInsertException(String massage) {
        super(massage);
    }
}
