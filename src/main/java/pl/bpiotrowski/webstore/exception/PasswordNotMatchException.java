package pl.bpiotrowski.webstore.exception;

public class PasswordNotMatchException extends RuntimeException{

    public PasswordNotMatchException() {
        super("Passwords doesnt match");
    }

}
