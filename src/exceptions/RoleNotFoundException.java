package exceptions;

public class RoleNotFoundException extends Exception{

    public RoleNotFoundException() {
        super("role not found");
    }
}
