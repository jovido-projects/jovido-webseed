package biz.jovido.seed.content.model.node.field.constraint;

import biz.jovido.seed.content.model.node.field.Constraint;
import biz.jovido.seed.content.model.node.fragment.payload.DatePayload;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Stephan Grundner
 */
@Table(name = "temporal_constraint")
@DiscriminatorValue("temporal")
@Entity
public class TemporalConstraint extends Constraint {

    public TemporalConstraint() {
        addSupportedPayloadType(DatePayload.class);
    }
}
