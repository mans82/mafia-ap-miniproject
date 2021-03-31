package exceptions;

public class PlayerNotFoundException extends Exception{

    public PlayerNotFoundException() {
        super("user not found");
    }
}
