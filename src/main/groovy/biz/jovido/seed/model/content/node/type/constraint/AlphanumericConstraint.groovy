package biz.jovido.seed.model.content.node.type.constraint

import biz.jovido.seed.model.content.node.type.Constraint
import biz.jovido.seed.model.content.node.fragment.payload.TextPayload

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.Table

/**
 *
 * @author Stephan Grundner
 */
@Table(name = 'alphanumeric_constraint')
@DiscriminatorValue('alphanumeric')
@Entity
class AlphanumericConstraint extends Constraint {

    boolean multiline
    boolean html

    AlphanumericConstraint() {
        addSupportedPayloadType(TextPayload)
    }
}
