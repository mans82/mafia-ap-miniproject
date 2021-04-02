package exceptions;

public class PlayerNotFoundException extends MafiaException{

    public PlayerNotFoundException() {
        super("user not found");
    }
}
