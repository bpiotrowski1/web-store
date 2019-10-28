package pl.bpiotrowski.webstore.exception;

public class EntityNotFoundException extends RuntimeException{

    private Long id;
    private String message;

    public EntityNotFoundException(Long id) {
        super("Entity with id=" + id + " not found");
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
