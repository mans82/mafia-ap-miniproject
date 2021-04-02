package players;

import exceptions.CannotPlayException;
import exceptions.TargetIsMafiaException;

public class Mafia extends Player{

    private Player playerVotedToKill = null;

    public Mafia(String name) {
        super(name);
    }

    @Override
    public String getRoleName() {
        return "Mafia";
    }

    @Override
    public void playOn(Player player) throws CannotPlayException, TargetIsMafiaException {
        if (player.isDead()) {
            throw new CannotPlayException("votee already dead");
        }
        if (player instanceof Mafia) {
            // Should not be able to vote!
            throw new TargetIsMafiaException();
        }
        this.playerVotedToKill = player;
    }

    @Override
    public void resetState() {
        super.resetState();
        this.playerVotedToKill = null;
    }

    @Override
    public boolean isMafia() {
        return true;
    }

    public Player getPlayerVotedToKill() {
        return playerVotedToKill;
    }
}
