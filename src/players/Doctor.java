package players;


public class Doctor extends Player{

    private Player savedPlayer = null;

    public Doctor(String name) {
        super(name);
    }

    @Override
    public void playOn(Player player) {
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
