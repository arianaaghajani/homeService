package ir.ariana.hw19spring.exception;

public class WrongInputPriceException extends RuntimeException {
    public WrongInputPriceException(String massage){
        super(massage);
    }
}
