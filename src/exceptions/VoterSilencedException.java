package exceptions;

public class VoterSilencedException extends MafiaException{

    public VoterSilencedException() {
        super("Voter is silenced.");
    }
}
