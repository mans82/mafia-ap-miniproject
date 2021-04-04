package players;

import exceptions.CannotPlayException;

public class Detective extends Player{

    private boolean hasQueried = false;

    public Detective(String name) {
        super(name);
    }

    @Override
    public String getRoleName() {
        return "Detective";
    }

    @Override
    public void playOn(Player player) throws CannotPlayException {
        if (this.hasQueried) {
            throw new CannotPlayException("Detective has already asked.");
        }
        if (player.isDead()) {
            throw new CannotPlayException("Suspect is dead.");
        }
        System.out.println(player.isMafia() ? "Yes..." : "No!");
        this.hasQueried = true;
    }

    @Override
    public void resetState() {
        super.resetState();
        this.hasQueried = false;
    }
}
