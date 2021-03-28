package players;

import exceptions.CannotPlayException;

public class Villager extends Player{

    protected Villager(String name) {
        super(name);
    }

    @Override
    public void playOn(Player player) throws CannotPlayException {
        throw new CannotPlayException(player.getName() + "is citizen");
    }
}
