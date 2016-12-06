package biz.jovido.seed.content.model.node.structure.constraint;

import biz.jovido.seed.content.model.node.fragment.property.NodeProperty;
import biz.jovido.seed.content.model.node.structure.Constraints;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Stephan Grundner
 */
//@Table(name = "reference_constraint")
@DiscriminatorValue("node")
@Entity
public class NodeConstraints extends Constraints {

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "allowed_structure",
            joinColumns = @JoinColumn(name = "constraints_id", referencedColumnName = "id"))
    @Column(name = "structure_name")
    private final Set<String> allowedStructures = new LinkedHashSet<>();

    public Set<String> getAllowedStructures() {
        return allowedStructures;
    }

    public NodeConstraints() {
        super(NodeProperty.class);
    }
}
