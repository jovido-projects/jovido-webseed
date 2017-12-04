package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class TextPayloadAttribute extends ValuePayloadAttribute<TextPayload> {

    private boolean multiline;

    public boolean isMultiline() {
        return multiline;
    }

    public void setMultiline(boolean multiline) {
        this.multiline = multiline;
    }

    @Override
    public TextPayload createPayload() {
        return new TextPayload();
    }
}
