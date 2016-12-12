package biz.jovido.seed.content.model.node.fragment.property;

import biz.jovido.seed.content.model.Node;
import biz.jovido.seed.content.model.NodeBundle;
import biz.jovido.seed.content.model.node.fragment.Property;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
            inverseJoinColumns = @JoinColumn(name = "node_bundle_id", nullable = true))
    private final List<NodeBundle> values = new ArrayList<>();

    @Override
    public List<Node> getValues() {
        return values.stream()
                .map(NodeBundle::getCurrent)
                .collect(Collectors.toList());
    }

    public NodeProperty() {
        super(Node.class);
    }
}
