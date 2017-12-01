package biz.jovido.seed;

import org.springframework.context.MessageSource;

/**
 * @author Stephan Grundner
 */
public interface MessageSourceProvider {

    MessageSource getMessageSource();
}
