package players;

import exceptions.CannotWakeUpException;

public class Citizen extends Player{

    protected Citizen(String name) {
        super(name);
    }

    @Override
    public void playOn(Player player) throws CannotWakeUpException {
        throw new CannotWakeUpException(player.getName() + "is citizen");
    }
}
