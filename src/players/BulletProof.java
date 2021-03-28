package players;

import exceptions.CannotPlayException;
import exceptions.CannotWakeUpException;

public class BulletProof extends Player{

    private boolean usedExtraHealth = false;

    protected BulletProof(String name) {
        super(name);
    }

    @Override
    public void playOn(Player player) throws CannotWakeUpException {
        throw new CannotWakeUpException();
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
