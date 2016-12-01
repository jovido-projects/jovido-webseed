package biz.jovido.seed.content.model.node.fragment.payload;

import biz.jovido.seed.content.model.node.fragment.Payload;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Lob;

/**
 * @author Stephan Grundner
 */
@DiscriminatorValue(Payload.Type.TEXT)
@Entity
public class TextPayload extends Payload<String> {

    @Lob
    @Column(name = "text_value")
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
