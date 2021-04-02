package players;

import exceptions.CannotWakeUpException;

public class Joker extends Player{

    public Joker(String name) {
        super(name);
    }

    @Override
    public String getRoleName() {
        return "Joker";
    }

    @Override
    public void playOn(Player player) throws CannotWakeUpException {
        throw new CannotWakeUpException();
    }

}
