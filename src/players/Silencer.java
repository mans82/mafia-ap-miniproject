package players;

import exceptions.CannotPlayException;
import exceptions.TargetIsMafiaException;

public class Silencer extends Mafia{

    private Player silencedPlayer = null;
    private boolean hasSilenced = false;

    public Silencer(String name) {
        super(name);
    }

    @Override
    public String getRoleName() {
        return "Silencer";
    }

    @Override
    public void playOn(Player player) throws CannotPlayException, TargetIsMafiaException {
        if (hasSilenced) {
            super.playOn(player);
        } else {
            if (player.isDead()) {
                throw new CannotPlayException("target is already dead");
            }
            if (this.silencedPlayer != null) {
                this.silencedPlayer.silenced = false;
            }
            this.silencedPlayer = player;
            this.silencedPlayer.silenced = true;
            this.hasSilenced = true;
        }
    }

    @Override
    public void resetState() {
        super.resetState();
        this.silencedPlayer = null;
        this.hasSilenced = false;
    }

    public Player getSilencedPlayer() {
        return silencedPlayer;
    }
}
