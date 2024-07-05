package ir.ariana.hw19spring.exception;

public class WrongImageInputException extends RuntimeException {
    public WrongImageInputException(String massage) {
        super(massage);
    }
}
