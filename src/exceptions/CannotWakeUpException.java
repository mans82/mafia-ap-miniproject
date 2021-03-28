package exceptions;

public class CannotWakeUpException extends Exception{

    public CannotWakeUpException() {
        super();
    }

    public CannotWakeUpException(String message) {
        super(message);
    }
}
