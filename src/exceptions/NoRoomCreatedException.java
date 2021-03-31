package exceptions;

public class NoRoomCreatedException extends Exception{

    public NoRoomCreatedException() {
        super("no game created");
    }
}
