package pl.bpiotrowski.webstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "User is not active")
public class UserIsNotActiveException extends RuntimeException {

    public UserIsNotActiveException(String username) {
        super("User is not active. USER ID: " + username + ". Active your account by your email.");
    }

}
