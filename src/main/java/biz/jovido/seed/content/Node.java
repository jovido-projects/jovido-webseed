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
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"branch_id", "item_id"}))
public class Node {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    @Convert(converter = UUIDConverter.class)
    private UUID uuid;

    @ManyToOne(optional = false, cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @ManyToOne
    private Node parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    private final List<Node> children = new ArrayList<>();

    @Column(length = 255 * 8)
    private String label;

    @ManyToOne(optional = false, cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "history_id")
    private History history;

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

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    public boolean belongsTo(Item item) {
        History history = getHistory();
        return ItemUtils.areTheSame(history.getPublished(), item) ||
                ItemUtils.areTheSame(history.getCurrent(), item);
    }

    public Node copy() {
        Node copy = new Node();
        copy.setUuid(UUID.randomUUID());
        copy.setBranch(getBranch());
        copy.setLabel(getLabel());
        copy.setParent(getParent());

        return copy;
    }
}
