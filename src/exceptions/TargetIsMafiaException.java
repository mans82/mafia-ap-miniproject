package exceptions;

public class TargetIsMafiaException extends MafiaException{
    public TargetIsMafiaException() {
        super("voted player is mafia");
    }
}
