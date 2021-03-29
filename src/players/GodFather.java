package players;


public class GodFather extends Mafia{

    public GodFather(String name) {
        super(name);
    }

    @Override
    public String getRoleName() {
        return "GodFather";
    }

    @Override
    public boolean isMafia() {
        return false;
    }
}
