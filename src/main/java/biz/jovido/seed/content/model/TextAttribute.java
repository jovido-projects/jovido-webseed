package biz.jovido.seed.content.model;

/**
 * @author Stephan Grundner
 */
public class TextAttribute extends Attribute {

    private boolean multiline;

    public boolean isMultiline() {
        return multiline;
    }

    public void setMultiline(boolean multiline) {
        this.multiline = multiline;
    }

    @Override
    public Payload createPayload() {
        return new TextPayload();
    }
}
