package biz.jovido.seed.content;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.*;

/**
 * @author Stephan Grundner
 */
@Entity
public class Item {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Leaf leaf;

    @ManyToOne(optional = false)
    private Structure structure;

    @Column(length = 255 * 8)
    private String label;

//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinTable(name = "item_node",
//            joinColumns = @JoinColumn(name = "item_id"),
//            inverseJoinColumns = @JoinColumn(name = "node_id"),
//            uniqueConstraints = @UniqueConstraint(columnNames = {"item_id", "node_id"}))
//    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @Fetch(FetchMode.SELECT)
//    private final List<Node> nodes = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private final List<Node> nodes = new ArrayList<>();

    @OneToMany(mappedBy = "item", targetEntity = Sequence.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @MapKey(name = "attributeName")
    private final Map<String, Sequence> sequences = new HashMap<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Leaf getLeaf() {
        return leaf;
    }

    public void setLeaf(Leaf leaf) {
        this.leaf = leaf;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Node> getNodes() {
        return Collections.unmodifiableList(nodes);
    }

//    TODO Check if node for same branch already exists, and if so, throw exception!
    public boolean addNode(Node node) {
        return nodes.add(node);
    }

    public Map<String, Sequence> getSequences() {
        return Collections.unmodifiableMap(sequences);
    }

    public Sequence getSequence(String attributeName) {
        return sequences.get(attributeName);
    }

    public void setSequence(String attributeName, Sequence sequence) {
        Sequence replaced = sequences.put(attributeName, sequence);
        if (replaced != null) {
            replaced.setItem(null);
            replaced.setAttributeName(null);
        }

        if (sequence != null) {
            sequence.setItem(this);
            sequence.setAttributeName(attributeName);
        }
    }

    public Locale getLocale() {
        Leaf chronicle = getLeaf();
        if (chronicle != null) {

            return chronicle.getLocale();
        }

        return null;
    }
}
