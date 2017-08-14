package biz.jovido.seed.content.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Stephan Grundner
 */
@Entity
@DiscriminatorValue("text")
public class TextPayload extends Payload {

    private String text;

    @Override
    public String getValue() {
        return text;
    }

    @Override
    public void setValue(Object data) {
        text = (String) data;
    }
}
