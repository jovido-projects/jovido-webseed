package biz.jovido.seed.content.model;

import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Stephan Grundner
 */
@Table(name = "node_bundle")
@Entity
public class NodeBundle implements Bundle<Node> {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "current_node_id")
    private Node current;

    @OneToMany(mappedBy = "bundle")
    private final Set<Node> revisions = new LinkedHashSet<>();

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

    public Set<Node> getRevisions() {
        return Collections.unmodifiableSet(revisions);
    }

    public boolean addRevision(Node revision) {
        NodeBundle bundle = revision.getBundle();
        if (bundle == null) {
            revision.setBundle(this);
        }

        Assert.isTrue(revision.getBundle() == this);

        if (current == null) {
            current = revision;
        }

        return revisions.add(revision);
    }
}
