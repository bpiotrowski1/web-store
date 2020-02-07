package pl.bpiotrowski.webstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "File is empty")
public class FileEmptyException extends RuntimeException {

    public FileEmptyException() {
        super("File is empty. Check location of file.");
    }

}
