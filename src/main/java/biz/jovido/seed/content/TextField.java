package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class TextField extends Field {

    private boolean multiline;

    public boolean isMultiline() {
        return multiline;
    }

    public void setMultiline(boolean multiline) {
        this.multiline = multiline;
    }

    @Override
    protected Payload createPayload() {
        return new TextPayload();
    }

    public TextField(String propertyName) {
        super(propertyName);
    }
}
