package biz.jovido.seed.content.model.node.fragment.payload;

import biz.jovido.seed.content.model.node.fragment.Payload;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigInteger;

/**
 * @author Stephan Grundner
 */
@DiscriminatorValue(Payload.Type.INTEGER)
@Entity
public class IntegerPayload extends NumberPayload<BigInteger> {

    @Column(name = "integer_value")
    private BigInteger value;

    public BigInteger getValue() {
        return value;
    }

    public void setValue(BigInteger value) {
        this.value = value;
    }
}
