package exceptions;

public class CannotWakeUpException extends Exception{

    public CannotWakeUpException() {
        super("user can not wake up during night");
    }
}
