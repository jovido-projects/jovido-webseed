package biz.jovido.seed.uimodel;

import org.springframework.context.MessageSource;

import java.util.Locale;

/**
 * @author Stephan Grundner
 */
public final class ResolvableText implements Text {

    private final MessageSource messageSource;

    private String messageCode;
    private String defaultMessage;
    private Object[] messageArgs;
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

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public Object[] getMessageArgs() {
        return messageArgs;
    }

    public void setMessageArgs(Object[] messageArgs) {
        this.messageArgs = messageArgs;
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

    public ResolvableText(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
