package domain.exceptions;

public class ExistentRecordException extends RuntimeException {
    public ExistentRecordException(String message) {
        super(message);
    }
}
