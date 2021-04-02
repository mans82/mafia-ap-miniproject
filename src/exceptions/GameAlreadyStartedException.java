package exceptions;

public class GameAlreadyStartedException extends MafiaException{

    public GameAlreadyStartedException() {
        super("game has already started");
    }
}
