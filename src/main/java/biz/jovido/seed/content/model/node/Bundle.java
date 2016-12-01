package biz.jovido.seed.content.model.node;

import biz.jovido.seed.content.model.Node;

import javax.persistence.*;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Stephan Grundner
 */
@Table(name = "node_bundle")
@Entity
public class Bundle {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "current_node_id", nullable = false)
    private Node current;

    @OneToMany(mappedBy = "bundle")
    private final Set<Node> nodes = new LinkedHashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Node getCurrent() {
        return current;
    }

    public void setCurrent(Node current) {
        this.current = current;
    }

    public Set<Node> getNodes() {
        return Collections.unmodifiableSet(nodes);
    }
}
