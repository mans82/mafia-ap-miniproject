package exceptions;

public class CannotVoteException extends Exception{

    public CannotVoteException() {
        super();
    }

    public CannotVoteException(String message) {
        super(message);
    }
}
