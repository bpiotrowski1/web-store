package pl.bpiotrowski.webstore.exception;

public class QuantityBelowZeroException extends RuntimeException{

    public QuantityBelowZeroException(Long id) {
        super("Product " + id + " quantity below zero");
    }

}
