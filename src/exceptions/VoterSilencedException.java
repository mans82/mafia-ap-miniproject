package exceptions;

public class VoterSilencedException extends Exception{

    public VoterSilencedException() {
        super("voter is silenced");
    }
}
