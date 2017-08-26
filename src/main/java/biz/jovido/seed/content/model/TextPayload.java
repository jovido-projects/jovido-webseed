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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
