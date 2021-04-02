package players;


import exceptions.CannotPlayException;

public class Doctor extends Player{

    private Player savedPlayer = null;

    public Doctor(String name) {
        super(name);
    }

    @Override
    public String getRoleName() {
        return "Doctor";
    }

    @Override
    public void playOn(Player player) throws CannotPlayException {
        if (player.isDead()) {
            throw new CannotPlayException("target already dead");
        }
        this.savedPlayer = player;
    }

    @Override
    public void resetState() {
        super.resetState();
        this.savedPlayer = null;
    }

    public Player getSavedPlayer() {
        return savedPlayer;
    }
}
