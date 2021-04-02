package exceptions;

public class NoRoomCreatedException extends MafiaException{

    public NoRoomCreatedException() {
        super("no game created");
    }
}
