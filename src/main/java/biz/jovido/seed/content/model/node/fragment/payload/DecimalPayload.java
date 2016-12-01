package biz.jovido.seed.content.model.node.fragment.payload;

import biz.jovido.seed.content.model.node.fragment.Payload;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;

/**
 * @author Stephan Grundner
 */
@DiscriminatorValue(Payload.Type.DECIMAL)
@Entity
public class DecimalPayload extends NumberPayload<BigDecimal> {

    @Column(name = "decimal_value")
    private BigDecimal value;

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
