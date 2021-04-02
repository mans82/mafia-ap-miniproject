package exceptions;

public class NoRoleException extends MafiaException{

    public NoRoleException() {
        super("one or more players do not have a role");
    }
}
