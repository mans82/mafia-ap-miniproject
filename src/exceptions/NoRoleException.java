package exceptions;

public class NoRoleException extends MafiaException{

    public NoRoleException() {
        super("One or more players do not have a role.");
    }
}
