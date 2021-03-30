package exceptions;

public class GameAlreadyStartedException extends Exception{

    public GameAlreadyStartedException() {
        super("game has already started");
    }
}
