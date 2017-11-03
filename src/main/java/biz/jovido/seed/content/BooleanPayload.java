package biz.jovido.seed.content;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Stephan Grundner
 */
@Entity
@DiscriminatorValue("Boolean")
public class BooleanPayload extends Payload<Boolean> {

    private String value;

    @Override
    public Boolean getValue() {
        return Boolean.parseBoolean(value);
    }

    @Override
    public void setValue(Boolean value) {
        this.value = value.toString();
    }
}
