package players;

import exceptions.CannotPlayException;
import exceptions.CannotWakeUpException;
import exceptions.TargetIsMafiaException;

public abstract class Player {
    private final String name;
    private int voteCount = 0;
    private boolean voted = false;
    protected boolean silenced = false;
    private boolean isDead = false;

    protected Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract String getRoleName();

    public void vote(Player votee) throws IllegalStateException, CannotPlayException {
        if (this.voted) {
            throw new IllegalStateException("Player has already voted.");
        }
        if (votee.isDead()) {
            throw new CannotPlayException("Votee has already died.");
        }
        votee.voteCount++;
        this.voted = true;
    }

    public void resetState() {
        this.voteCount = 0;
        this.voted = false;
        this.silenced = false;
    }

    public void die() {
        this.isDead = true;
    }

    public boolean isDead() {
        return isDead;
    }

    public boolean isMafia() {
        return false;
    }

    public abstract void playOn(Player player) throws CannotPlayException, CannotWakeUpException, TargetIsMafiaException;

    public int getVoteCount() {
        return voteCount;
    }

    public boolean isSilenced() {
        return silenced;
    }
}
