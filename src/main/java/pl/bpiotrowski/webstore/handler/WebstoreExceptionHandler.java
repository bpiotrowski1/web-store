package pl.bpiotrowski.webstore.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.bpiotrowski.webstore.exception.QuantityBelowZeroException;

@Slf4j
@RestControllerAdvice
public class WebstoreExceptionHandler {

    @ExceptionHandler(QuantityBelowZeroException.class)
    public String quantityBelowZeroException(QuantityBelowZeroException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }

}
