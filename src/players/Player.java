package players;

import exceptions.CannotVoteException;
import exceptions.CannotWakeUpException;

public abstract class Player {
    private final String name;
    private int voteCount = 0;
    private boolean voted = false;
    private boolean isDead = false;

    protected Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void vote(Player votee) throws IllegalStateException{
        if (this.voted) {
            throw new IllegalStateException("Player already voted");
        }
        votee.voteCount++;
        this.voted = true;
    }

    public void resetState() {
        this.voteCount = 0;
        this.voted = false;
    }

    public void die() {
        this.isDead = true;
    }

    public boolean isDead() {
        return isDead;
    }

    public abstract void playOn(Player player) throws CannotWakeUpException, CannotVoteException;
}
