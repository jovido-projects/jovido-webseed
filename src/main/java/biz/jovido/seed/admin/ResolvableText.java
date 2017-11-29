package biz.jovido.seed.admin;

import org.springframework.context.MessageSource;

import java.util.Locale;

/**
 * @author Stephan Grundner
 */
public final class ResolvableText implements Text {

    private MessageSource messageSource;

    private String messageCode;
    private Object[] messageArgs;
    private String defaultMessage;
    private Locale locale;

    public MessageSource getMessageSource() {
        return messageSource;
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
        return messageSource.getMessage(messageCode, messageArgs, defaultMessage, locale);
    }

    public ResolvableText(String messageCode, Object[] messageArgs, String defaultMessage, Locale locale) {
        this.messageCode = messageCode;
        this.messageArgs = messageArgs;
        this.defaultMessage = defaultMessage;
        this.locale = locale;
    }

    public ResolvableText(String messageCode, Object[] messageArgs, String defaultMessage) {
        this(messageCode, messageArgs, defaultMessage, null);
    }

    public ResolvableText(String messageCode, String defaultMessage) {
        this(messageCode, null, defaultMessage);
    }

    public ResolvableText(String messageCode) {
        this(messageCode, null);
    }

    public ResolvableText(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
