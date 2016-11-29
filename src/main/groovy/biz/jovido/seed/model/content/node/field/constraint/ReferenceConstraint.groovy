package biz.jovido.seed.model.content.node.field.constraint

import biz.jovido.seed.model.content.node.field.Constraint
import biz.jovido.seed.model.content.node.fragment.payload.NodePayload

import javax.persistence.CollectionTable
import javax.persistence.DiscriminatorValue
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Table

/**
 *
 * @author Stephan Grundner
 */
@Table(name = 'reference_constraint')
@DiscriminatorValue('reference')
@Entity
class ReferenceConstraint extends Constraint {

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = 'reference_constraint_structure')
    final Set<String> allowedStructureNames = new LinkedHashSet<>()

    Set<String> getAllowedStructureNames() {
        Collections.unmodifiableSet(allowedStructureNames)
    }

    boolean addAllowedStructureName(String structureName) {
        allowedStructureNames.add(structureName)
    }

    boolean removeAllowedStructureName(String structureName) {
        allowedStructureNames.remove(structureName)
    }

    ReferenceConstraint() {
        addSupportedPayloadType(NodePayload)
    }
}
