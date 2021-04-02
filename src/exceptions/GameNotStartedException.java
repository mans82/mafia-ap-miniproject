package exceptions;

public class GameNotStartedException extends MafiaException {

    public GameNotStartedException() {
        super("game has not started yet");
    }
}
