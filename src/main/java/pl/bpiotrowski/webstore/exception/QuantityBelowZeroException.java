package pl.bpiotrowski.webstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Quantity of product is below zero")
public class QuantityBelowZeroException extends RuntimeException {

    public QuantityBelowZeroException(Long id) {
        super("Product ID: " + id + " - quantity is below zero");
    }

}
