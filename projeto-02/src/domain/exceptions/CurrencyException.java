package domain.exceptions;

public class CurrencyException extends RuntimeException{
    public CurrencyException(String message) {
        super(message);
    }
}
