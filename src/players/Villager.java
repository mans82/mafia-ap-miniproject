package players;

import exceptions.CannotPlayException;
import exceptions.CannotWakeUpException;

public class Villager extends Player{

    public Villager(String name) {
        super(name);
    }

    @Override
    public void playOn(Player player) throws CannotWakeUpException {
        throw new CannotWakeUpException();
    }
}
