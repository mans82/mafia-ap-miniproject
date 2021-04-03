package exceptions;

public class NoRoomCreatedException extends MafiaException{

    public NoRoomCreatedException() {
        super("No game created.");
    }
}
