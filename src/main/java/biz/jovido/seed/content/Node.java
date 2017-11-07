package biz.jovido.seed.content;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Stephan Grundner
 */
@Entity
public class Node {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false, cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private Hierarchy hierarchy;

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private Node parent;

    private int ordinal = -1;

    @OneToMany(mappedBy = "parent", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
//    @OrderBy("ordinal")
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

    public Hierarchy getHierarchy() {
        return hierarchy;
    }

    /*public*/ void setHierarchy(Hierarchy hierarchy) {
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
        return Collections.unmodifiableList(children).stream()
                .sorted(Comparator.comparingInt(Node::getOrdinal))
                .collect(Collectors.toList());
    }

    public boolean addChild(Node child) {
        if (children.add(child)) {
            child.parent = this;
            child.ordinal = children.size() - 1;
            return true;
        }

        return false;
    }

    private void updateOrdinals(int start) {
        for (int i = start; i < children.size(); i++) {
            Node child = children.get(i);
            child.ordinal = i;
        }
    }

    public void removeChild(int index) {
        Node removed = children.remove(index);
        if (removed != null) {
            removed.parent = null;
            removed.ordinal = -1;
            updateOrdinals(index);
        }
    }

    public void removeChild(Node node) {
        int index = children.indexOf(node);
        if (index >= 0) {
            removeChild(index);
        }
    }

    public boolean isRoot() {
        return getParent() == null;
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

    public List<Node> getNodesAtSameLevel() {
        return isRoot() ?
                getHierarchy().getRootNodes() :
                getParent().getChildren();
    }

    public boolean isFirst() {
        return getOrdinal() == 0;
    }

    public boolean isLast() {
        return getOrdinal() == (getNodesAtSameLevel().size() - 1);
    }

    public Node getPreviousSibling() {
        List<Node> nodes = getNodesAtSameLevel();
        int index = nodes.indexOf(this);
        if (index > 0) {
            return nodes.get(index - 1);
        }

        return null;
    }

    public Node getNextSibling() {
        List<Node> nodes = getNodesAtSameLevel();
        int index = nodes.indexOf(this);
        if (++index < nodes.size()) {
            return nodes.get(index);
        }

        return null;
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
        copy.setHierarchy(getHierarchy());
        copy.setParent(getParent());

        return copy;
    }
}
