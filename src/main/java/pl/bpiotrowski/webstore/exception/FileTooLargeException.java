package pl.bpiotrowski.webstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "File is too large")
public class FileTooLargeException extends RuntimeException {

    public FileTooLargeException() {
        super("File is too large. Check size of file.");
    }

}
