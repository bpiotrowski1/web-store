package pl.bpiotrowski.webstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Bad file format")
public class BadFileExtensionException extends RuntimeException {

    public BadFileExtensionException() {
        super("Bad file format. Check extension");
    }

}
