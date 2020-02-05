package pl.bpiotrowski.webstore.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.bpiotrowski.webstore.exception.ProductHiddenException;
import pl.bpiotrowski.webstore.exception.QuantityBelowZeroException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class WebstoreExceptionHandler {

    @ExceptionHandler(QuantityBelowZeroException.class)
    public String quantityBelowZeroException(QuantityBelowZeroException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(ProductHiddenException.class)
    public String productHiddenException(ProductHiddenException e) {
        log.error(e.getMessage());
        return e.getMessage();
    }

    @ExceptionHandler(BindException.class)
    public Map<String, String> bindException(BindException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        Map<String, String> result = new HashMap<>();
        for(FieldError fieldError : fieldErrors) {
            result.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        log.error(result.toString());
        return result;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String missingServletRequestParameterException(MissingServletRequestParameterException e) {
        String errorMessage = "Required page number as parameter 'p'. " + e.getMessage();
        log.error(errorMessage);
        return errorMessage;
    }

}
