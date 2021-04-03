package exceptions;

public class GameAlreadyStartedException extends MafiaException{

    public GameAlreadyStartedException() {
        super("Game has already started.");
    }
}
