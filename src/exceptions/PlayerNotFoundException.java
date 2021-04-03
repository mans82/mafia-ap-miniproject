package exceptions;

public class PlayerNotFoundException extends MafiaException{

    public PlayerNotFoundException() {
        super("User not found.");
    }
}
