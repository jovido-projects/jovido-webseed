package biz.jovido.seed.model.content.node.type.constraint

import biz.jovido.seed.model.content.node.type.Constraint
import biz.jovido.seed.model.content.node.fragment.payload.DatePayload

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.Table

/**
 *
 * @author Stephan Grundner
 */
@Table(name = 'temporal_constraint')
@DiscriminatorValue('temporal')
@Entity
class TemporalConstraint extends Constraint {

    TemporalConstraint() {
        addSupportedPayloadType(DatePayload)
    }
}
