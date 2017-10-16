package biz.jovido.seed.content;

import javax.persistence.Entity;

/**
 * @author Stephan Grundner
 */
@Entity
public class TextPayload extends Payload<String> {

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getValue() {
        return text;
    }
}
