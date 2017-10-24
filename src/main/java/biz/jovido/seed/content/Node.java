package biz.jovido.seed.content;

import biz.jovido.seed.UUIDConverter;

import javax.persistence.*;
import java.util.ArrayList;
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

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
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

    public List<Node> getChildren() {
        return children;
    }

    public boolean addChild(Node child) {
        if (children.add(child)) {
            child.parent = this;
            return true;
        }

        return false;
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

    public void setLeaf(Leaf leaf) {
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
