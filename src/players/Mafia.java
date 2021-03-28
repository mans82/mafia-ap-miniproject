package players;

import exceptions.CannotVoteException;

public class Mafia extends Player{

    private Player playerVotedToKill;

    protected Mafia(String name) {
        super(name);
    }

    @Override
    public void playOn(Player player) throws CannotVoteException {
        if (player.isDead()) {
            throw new CannotVoteException("votee already dead");
        }
        this.playerVotedToKill = player;
    }
}
