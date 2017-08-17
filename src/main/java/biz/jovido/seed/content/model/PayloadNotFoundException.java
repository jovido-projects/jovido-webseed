package biz.jovido.seed.content.model;

/**
 * @author Stephan Grundner
 */
public class PayloadNotFoundException extends RuntimeException {

    public PayloadNotFoundException() {}

    public PayloadNotFoundException(String message) {
        super(message);
    }

    public PayloadNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PayloadNotFoundException(Throwable cause) {
        super(cause);
    }

    public PayloadNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
