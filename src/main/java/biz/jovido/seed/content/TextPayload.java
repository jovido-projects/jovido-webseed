package biz.jovido.seed.content;

import javax.persistence.Entity;

/**
 * @author Stephan Grundner
 */
@Entity
public class TextPayload extends Payload {

    private String text;

    @Override
    public Object getValue() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
