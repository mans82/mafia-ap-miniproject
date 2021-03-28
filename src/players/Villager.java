package players;

import exceptions.CannotWakeUpException;

public class Villager extends Player{

    protected Villager(String name) {
        super(name);
    }

    @Override
    public void playOn(Player player) throws CannotWakeUpException {
        throw new CannotWakeUpException(player.getName() + "is citizen");
    }
}
