package exceptions;

public class GameNotStartedException extends MafiaException {

    public GameNotStartedException() {
        super("Game has not started yet.");
    }
}
