package biz.jovido.seed.content;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Lob;

/**
 * @author Stephan Grundner
 */
@Entity
@DiscriminatorValue("text")
public class TextPayload extends Payload {

    @Lob
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public Payload copy() {
        TextPayload copy = new TextPayload();
        copy.setText(getText());

        return copy;
    }

    public TextPayload() {
        super(PayloadType.TEXT);
    }
}
