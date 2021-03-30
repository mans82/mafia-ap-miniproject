package players;

import exceptions.CannotPlayException;

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
    public void playOn(Player player) throws CannotPlayException {
        if (player.isDead()) {
            throw new CannotPlayException("votee already dead");
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
