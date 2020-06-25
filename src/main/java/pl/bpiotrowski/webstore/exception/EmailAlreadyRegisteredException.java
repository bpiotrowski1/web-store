package pl.bpiotrowski.webstore.exception;

public class EmailAlreadyRegisteredException extends RuntimeException{

    public EmailAlreadyRegisteredException() {
        super("Email already registered");
    }

}
