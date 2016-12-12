package biz.jovido.seed.content.model.node;

import biz.jovido.seed.content.model.Bundle;
import biz.jovido.seed.content.model.Node;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Stephan Grundner
 */
@Table(name = "fragment_bundle")
@Entity
public class FragmentBundle implements Bundle<Fragment> {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "node_id")
    private Node node;

    @OneToOne
    @JoinColumn(name = "current_fragment_id")
    private Fragment current;

    @OneToMany(mappedBy = "bundle")
    private final Set<Fragment> revisions = new LinkedHashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Fragment getCurrent() {
        return current;
    }

    public void setCurrent(Fragment current) {
        this.current = current;
    }

    public Set<Fragment> getRevisions() {
        return Collections.unmodifiableSet(revisions);
    }

    public boolean addRevision(Fragment revision) {
        FragmentBundle bundle = revision.getBundle();
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
