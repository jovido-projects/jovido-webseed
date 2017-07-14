package biz.jovido.seed.content;

import javax.persistence.Entity;

/**
 * @author Stephan Grundner
 */
@Entity
public class TextPayload extends Payload<String> {

    private String text;

    @Override
    public String getValue() {
        return text;
    }

    @Override
    public void setValue(String value) {
        text = value;
    }
}
