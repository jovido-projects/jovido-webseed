package biz.jovido.seed.content;

import biz.jovido.seed.AbstractUnique;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@Entity
public class ItemHistory extends AbstractUnique {

    @OneToOne
    private Item published;

    @OneToOne(optional = false)
    private Item current;

    @OneToMany(mappedBy = "itemHistory", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private final List<Node> nodes = new ArrayList<>();

    @OneToMany(mappedBy = "history", fetch = FetchType.LAZY)
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

    public List<Node> getNodes() {
        return Collections.unmodifiableList(nodes);
    }

    public boolean addNode(Node node) {
        if (nodes.add(node)) {
            node.setItemHistory(this);
            return true;
        }

        return false;
    }

    public boolean removeNode(Node node) {
//        if (nodes.remove(node)) {
//        TODO durch idEquals o.ä. ersetzen:
        if (nodes.removeIf(it -> it.getId().equals(node.getId()))) {
            node.setItemHistory(null);
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
        if (!(o instanceof ItemHistory)) {
            return false;
        }

        ItemHistory history = (ItemHistory) o;
        return new EqualsBuilder()
                .append(getId(), history.getId())
                .isEquals();
    }
}
