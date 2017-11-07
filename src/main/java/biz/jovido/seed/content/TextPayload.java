package biz.jovido.seed.content;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Objects;

/**
 * @author Stephan Grundner
 */
@Entity
@DiscriminatorValue("Text")
public class TextPayload extends Payload<String> {

    private String value;

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean differsFrom(Payload<String> other) {
        if (other == null) {
            return true;
        }

        return !Objects.equals(getValue(), other.getValue());
    }
}
