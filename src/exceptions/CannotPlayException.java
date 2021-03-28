package exceptions;

public class CannotPlayException extends Exception{

    public CannotPlayException() {
        super();
    }

    public CannotPlayException(String message) {
        super(message);
    }
}
