package pl.bpiotrowski.webstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Product which tried to buy is hidden")
public class ProductHiddenException extends RuntimeException {

    public ProductHiddenException(Long id) {
        super("Product (ID: " + id + ") - which tried to buy is hidden");
    }

}
