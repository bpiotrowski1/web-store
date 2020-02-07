package pl.bpiotrowski.webstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "No address found")
public class NoAddressFoundException extends RuntimeException {

    public NoAddressFoundException(Long id) {
        super("No address found for USER ID: " + id + ". Fill address on user panel page.");
    }

}
