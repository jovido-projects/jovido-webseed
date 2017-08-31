package biz.jovido.seed.content.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@Entity
public class Node {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private Root root;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Node parent;
    private int ordinal;

    @OneToMany(mappedBy = "parent",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @OrderBy("ordinal")
    private final List<Node> children = new ArrayList<>();

//    @OneToOne
//    private Bundle bundle;

    @ManyToOne(optional = true)
    private Item item;

    public Long getId() {
        return id;
    }

    /*default*/ void setId(Long id) {
        this.id = id;
    }

    public Root getRoot() {
        return root;
    }

    /*default*/ void setRoot(Root root) {
        this.root = root;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getOrdinal() {
        return ordinal;
    }

    /*default*/ void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public List<Node> getChildren() {
        return Collections.unmodifiableList(children);
    }

    public Item getItem() {
        return item;
    }

    /*default*/ void setItem(Item item) {
        this.item = item;
    }
}
