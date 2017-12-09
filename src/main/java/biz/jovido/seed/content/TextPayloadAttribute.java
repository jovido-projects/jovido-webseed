package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class TextPayloadAttribute extends PayloadAttribute {

    private boolean multiline;

    public boolean isMultiline() {
        return multiline;
    }

    public void setMultiline(boolean multiline) {
        this.multiline = multiline;
    }
}
