package players;

import exceptions.CannotPlayException;

public class BulletProof extends Player{

    private boolean usedExtraHealth = false;

    protected BulletProof(String name) {
        super(name);
    }

    @Override
    public void playOn(Player player) throws CannotPlayException {
        throw new CannotPlayException("user cannot wake up during night");
    }

    @Override
    public void die(boolean isNight) {
        if (isNight && !usedExtraHealth) {
            this.usedExtraHealth = true;
        } else {
            super.die(false);
        }
    }
}
