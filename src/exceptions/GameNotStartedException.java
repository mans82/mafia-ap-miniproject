package exceptions;

public class GameNotStartedException extends Exception {

    public GameNotStartedException() {
        super("game has not started yet");
    }
}
