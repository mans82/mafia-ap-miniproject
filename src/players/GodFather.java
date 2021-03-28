package players;


public class GodFather extends Mafia{

    protected GodFather(String name) {
        super(name);
    }

    @Override
    public boolean isMafia() {
        return false;
    }
}
