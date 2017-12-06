package biz.jovido.seed.content;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Stephan Grundner
 */
@Entity
@DiscriminatorValue("boolean")
public class BooleanPayload extends Payload<Boolean> {

    @Column(name = "boolean_value")
    private Boolean value;

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public void setValue(Boolean value) {
        this.value = value;
    }
}
