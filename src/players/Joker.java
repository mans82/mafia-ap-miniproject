package players;

import exceptions.CannotWakeUpException;
import exceptions.JokerWonException;

public class Joker extends Player{

    protected Joker(String name) {
        super(name);
    }

    @Override
    public void playOn(Player player) throws CannotWakeUpException {
        throw new CannotWakeUpException();
    }

    @Override
    public void die(boolean isNight) throws JokerWonException {
        if (!isNight) {
            // Joker wins!
            throw new JokerWonException();
        }
        super.die(false);
    }
}
