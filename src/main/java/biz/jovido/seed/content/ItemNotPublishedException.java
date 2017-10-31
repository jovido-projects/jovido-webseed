package biz.jovido.seed.content;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Stephan Grundner
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason="No such item")
public class ItemNotPublishedException extends RuntimeException {

    public ItemNotPublishedException() {
    }

    public ItemNotPublishedException(String message) {
        super(message);
    }

    public ItemNotPublishedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ItemNotPublishedException(Throwable cause) {
        super(cause);
    }

    public ItemNotPublishedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
