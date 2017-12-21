package biz.jovido.seed.ui;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * @author Stephan Grundner
 */
public class MessageSourceResolvableText implements Text {

    private MessageSource messageSource;

    private String messageCode;
    private Object[] messageArgs;
    private Locale locale;
    private String defaultMessage;

    public MessageSource getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
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

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    @Override
    public String getValue() {
        Locale locale = getLocale();
        if (locale == null) {
            locale = LocaleContextHolder.getLocale();
        }

        return messageSource.getMessage(messageCode, messageArgs, defaultMessage, locale);
    }

    public MessageSourceResolvableText(String messageCode, Object[] messageArgs, String defaultMessage, Locale locale) {
        this.messageCode = messageCode;
        this.messageArgs = messageArgs;
        this.defaultMessage = defaultMessage;
        this.locale = locale;
    }

    public MessageSourceResolvableText(String messageCode, Object[] messageArgs, String defaultMessage) {
        this(messageCode, messageArgs, defaultMessage, null);
    }

    public MessageSourceResolvableText(String messageCode, String defaultMessage) {
        this(messageCode, null, defaultMessage);
    }
}
