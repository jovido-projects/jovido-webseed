package biz.jovido.seed.content.model.node.field.constraint;

import biz.jovido.seed.content.model.node.field.Constraint;
import biz.jovido.seed.content.model.node.fragment.payload.NodePayload;

import javax.persistence.*;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Stephan Grundner
 */
@Table(name = "reference_constraint")
@DiscriminatorValue("reference")
@Entity
public class ReferenceConstraint extends Constraint {

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "reference_constraint_structure")
    private final Set<String> allowedStructureNames = new LinkedHashSet<String>();

    public Set<String> getAllowedStructureNames() {
        return Collections.unmodifiableSet(allowedStructureNames);
    }

    public boolean addAllowedStructureName(String structureName) {
        return allowedStructureNames.add(structureName);
    }

    public boolean removeAllowedStructureName(String structureName) {
        return allowedStructureNames.remove(structureName);
    }

    public ReferenceConstraint() {
        addSupportedPayloadType(NodePayload.class);
    }
}
