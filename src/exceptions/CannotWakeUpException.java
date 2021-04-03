package exceptions;

public class CannotWakeUpException extends MafiaException{

    public CannotWakeUpException() {
        super("User can not wake up during night.");
    }
}
