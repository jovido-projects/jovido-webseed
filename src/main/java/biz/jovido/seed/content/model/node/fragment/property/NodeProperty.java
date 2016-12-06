package biz.jovido.seed.content.model.node.fragment.property;

import biz.jovido.seed.content.model.Node;
import biz.jovido.seed.content.model.node.fragment.Property;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@DiscriminatorValue("node")
@Entity
public class NodeProperty extends Property<Node> {

    @ManyToMany
    @OrderColumn(name = "ordinal")
    @JoinTable(name = "node_property",
            joinColumns = @JoinColumn(name = "property_id"),
            inverseJoinColumns = @JoinColumn(name = "node_id", nullable = true))
    private final List<Node> values = new ArrayList<>();

    @Override
    public List<Node> getValues() {
        return values;
    }

    public NodeProperty() {
        super(Node.class);
    }
}
