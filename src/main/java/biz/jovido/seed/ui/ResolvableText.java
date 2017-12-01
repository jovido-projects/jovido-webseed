package biz.jovido.seed.ui;

import biz.jovido.seed.MessageSourceProvider;
import org.springframework.context.MessageSource;

import java.util.Locale;

/**
 * @author Stephan Grundner
 */
public final class ResolvableText extends AbstractText {

    private final MessageSourceProvider messageSourceProvider;

    private String messageCode;
    private Object[] messageArgs;
    private String defaultMessage;
    private Locale locale;

    private MessageSource getMessageSource() {
        return messageSourceProvider.getMessageSource();
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public Object[] getMessageArgs() {
        return messageArgs;
    }

    public void setMessageArgs(Object[] messageArgs) {
        this.messageArgs = messageArgs;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public final String getValue() {
        return getMessageSource().getMessage(messageCode, messageArgs, defaultMessage, locale);
    }

    public ResolvableText(MessageSourceProvider messageSourceProvider, String messageCode, Object[] messageArgs, String defaultMessage, Locale locale) {
        this.messageSourceProvider = messageSourceProvider;
        this.messageCode = messageCode;
        this.messageArgs = messageArgs;
        this.defaultMessage = defaultMessage;
        this.locale = locale;
    }

    public ResolvableText(MessageSourceProvider messageSourceProvider, String messageCode, Object[] messageArgs, String defaultMessage) {
        this(messageSourceProvider, messageCode, messageArgs, defaultMessage, null);
    }

    public ResolvableText(MessageSourceProvider messageSourceProvider, String messageCode, String defaultMessage) {
        this(messageSourceProvider, messageCode, null, defaultMessage);
    }

    public ResolvableText(MessageSourceProvider messageSourceProvider, String messageCode) {
        this(messageSourceProvider, messageCode, null);
    }

    public ResolvableText(MessageSourceProvider messageSourceProvider) {
        this(messageSourceProvider, null, null);
    }
}
