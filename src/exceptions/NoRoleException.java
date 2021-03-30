package exceptions;

public class NoRoleException extends Exception{

    public NoRoleException() {
        super("one or more players do not have a role");
    }
}
