package biz.jovido.seed.content;

import biz.jovido.seed.AbstractUnique;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@Entity
public class Leaf extends AbstractUnique {

    @OneToOne
    private Item published;

    @OneToOne(optional = false)
    private Item current;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "leaf_host")
    private final List<Host> hosts = new ArrayList<>();

    @OneToMany(mappedBy = "leaf", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private final List<Node> nodes = new ArrayList<>();

    @OneToMany(mappedBy = "leaf", fetch = FetchType.LAZY)
    private final List<Item> items = new ArrayList<>();

    public Item getPublished() {
        return published;
    }

    public void setPublished(Item published) {
        this.published = published;
    }

    public Item getCurrent() {
        return current;
    }

    public void setCurrent(Item current) {
        this.current = current;
    }

    public List<Host> getHosts() {
        return Collections.unmodifiableList(hosts);
    }

    public List<Node> getNodes() {
        return Collections.unmodifiableList(nodes);
    }

    public boolean addNode(Node node) {
        if (nodes.add(node)) {
            node.setLeaf(this);
            return true;
        }

        return false;
    }

    public boolean removeNode(Node node) {
//        if (nodes.remove(node)) {
//        TODO durch idEquals o.ä. ersetzen:
        if (nodes.removeIf(it -> it.getId().equals(node.getId()))) {
            node.setLeaf(null);
            return true;
        }

        return false;
    }

    public List<Item> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Leaf)) {
            return false;
        }

        Leaf leaf = (Leaf) o;
        return new EqualsBuilder()
                .append(getId(), leaf.getId())
                .isEquals();
    }
}
