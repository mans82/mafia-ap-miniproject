package players;

import exceptions.CannotWakeUpException;

public class BulletProof extends Player{

    private boolean usedExtraHealth = false;

    public BulletProof(String name) {
        super(name);
    }

    @Override
    public String getRoleName() {
        return "BulletProof";
    }

    @Override
    public void playOn(Player player) throws CannotWakeUpException {
        throw new CannotWakeUpException();
    }

    public boolean usedExtraHealth() {
        return usedExtraHealth;
    }

    public void useExtraHealth() {
        this.usedExtraHealth = true;
    }

}
