package biz.jovido.seed.content;

import biz.jovido.seed.UUIDConverter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author Stephan Grundner
 */
@Entity
public class Node {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    @Convert(converter = UUIDConverter.class)
    private UUID uuid;

    @ManyToOne(optional = false, cascade = {}, fetch = FetchType.EAGER)
    private Hierarchy hierarchy;

    @ManyToOne
    private Node parent;
    private int ordinal = -1;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderBy("ordinal")
    @Fetch(FetchMode.SUBSELECT)
    private final List<Node> children = new ArrayList<>();

    @ManyToOne(optional = false, cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "leaf_id")
    private Leaf leaf;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Hierarchy getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(Hierarchy hierarchy) {
        this.hierarchy = hierarchy;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;

        if (parent != null) {
            parent.addChild(this);
        }
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public List<Node> getChildren() {
        return Collections.unmodifiableList(children);
    }

    public boolean addChild(Node child) {
        if (children.add(child)) {
            child.parent = this;
            child.ordinal = children.size() - 1;
            return true;
        }

        return false;
    }

    public void removeChild(int index) {
        Node removed = children.remove(index);
        if (removed != null) {
            removed.parent = null;
            removed.ordinal = -1;

            for (int i = index; i < children.size(); i++) {
                Node child = children.get(i);
                child.ordinal = i;
            }
        }
    }

    public void moveChild(int fromIndex, int toIndex) {
        Collections.swap(children, fromIndex, toIndex);
        for (int i = 0; i < children.size(); i++) {
            Node child = children.get(i);
            child.ordinal = i;
        }
    }

    public void removeChild(Node node) {
        int index = children.indexOf(node);
        if (index >= 0) {
            removeChild(index);
        }
    }

    public Node getRoot() {
        Node node = this;
        do {
            Node parent = node.getParent();
            if (parent != null) {
                node = parent;
                continue;
            }

            break;
        } while (true);

        return node;
    }

    public Leaf getLeaf() {
        return leaf;
    }

    /**/ void setLeaf(Leaf leaf) {
        this.leaf = leaf;
    }

    public boolean belongsTo(Item item) {
        Leaf leaf = getLeaf();
        return ItemUtils.areTheSame(leaf.getPublished(), item) ||
                ItemUtils.areTheSame(leaf.getCurrent(), item);
    }

    public Node copy() {
        Node copy = new Node();
        copy.setUuid(UUID.randomUUID());
        copy.setHierarchy(getHierarchy());
        copy.setParent(getParent());

        return copy;
    }
}
