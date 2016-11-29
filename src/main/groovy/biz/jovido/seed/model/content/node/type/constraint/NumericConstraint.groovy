package biz.jovido.seed.model.content.node.type.constraint

import biz.jovido.seed.model.content.node.type.Constraint
import biz.jovido.seed.model.content.node.fragment.payload.DecimalPayload
import biz.jovido.seed.model.content.node.fragment.payload.IntegerPayload

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.Table

/**
 *
 * @author Stephan Grundner
 */
@Table(name = 'numeric_constraint')
@DiscriminatorValue('numeric')
@Entity
class NumericConstraint extends Constraint {

    NumericConstraint() {
        addSupportedPayloadType(IntegerPayload)
        addSupportedPayloadType(DecimalPayload)
    }
}
