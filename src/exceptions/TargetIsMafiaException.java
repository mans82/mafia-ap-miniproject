package exceptions;

public class TargetIsMafiaException extends MafiaException{
    public TargetIsMafiaException() {
        super("Voted player is mafia.");
    }
}
