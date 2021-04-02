package exceptions;

public class CannotWakeUpException extends MafiaException{

    public CannotWakeUpException() {
        super("user can not wake up during night");
    }
}
