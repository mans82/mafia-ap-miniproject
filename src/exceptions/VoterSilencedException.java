package exceptions;

public class VoterSilencedException extends MafiaException{

    public VoterSilencedException() {
        super("voter is silenced");
    }
}
