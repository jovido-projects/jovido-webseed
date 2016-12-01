package biz.jovido.seed.content.model.node.field.constraint;

import biz.jovido.seed.content.model.node.field.Constraint;
import biz.jovido.seed.content.model.node.fragment.payload.DecimalPayload;
import biz.jovido.seed.content.model.node.fragment.payload.IntegerPayload;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Stephan Grundner
 */
@Table(name = "numeric_constraint")
@DiscriminatorValue("numeric")
@Entity
public class NumericConstraint extends Constraint {

    public NumericConstraint() {
        addSupportedPayloadType(IntegerPayload.class);
        addSupportedPayloadType(DecimalPayload.class);
    }
}
