package biz.jovido.seed.ui;

/**
 * @author Stephan Grundner
 */
public class Text {

    private String messageCode;
    private Object[] messageArguments;
    private String defaultMessage;

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public Object[] getMessageArguments() {
        return messageArguments;
    }

    public void setMessageArguments(Object[] messageArguments) {
        this.messageArguments = messageArguments;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public Text(String messageCode, Object[] messageArguments, String defaultMessage) {
        this.messageCode = messageCode;
        this.messageArguments = messageArguments;
        this.defaultMessage = defaultMessage;
    }

    public Text(String messageCode, String defaultMessage) {
        this(messageCode, null, defaultMessage);
    }

    public Text(String messageCode) {
        this(messageCode, null, null);
    }

    public Text() {}
}
