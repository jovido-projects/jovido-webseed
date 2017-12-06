package biz.jovido.seed.content;

import biz.jovido.seed.ui.Field;
import biz.jovido.seed.ui.Source;
import biz.jovido.seed.ui.TextField;

/**
 * @author Stephan Grundner
 */
public class TextPayloadAttribute extends PayloadAttribute<String> {

    private boolean multiline;

    public boolean isMultiline() {
        return multiline;
    }

    public void setMultiline(boolean multiline) {
        this.multiline = multiline;
    }

    @Override
    public Payload<String> createPayload() {
        return new TextPayload();
    }

    @Override
    public Field<String> createField(Source.Property<String> property) {
        return new TextField(property);
    }
}
