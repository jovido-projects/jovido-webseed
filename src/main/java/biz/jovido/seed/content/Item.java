package biz.jovido.seed.content;

import biz.jovido.seed.util.UnmodifiableMapProxy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.*;

/**
 * @author Stephan Grundner
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Item extends UnmodifiableMapProxy<String, Sequence> {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private History history;

    @ManyToOne(optional = false)
    private Structure structure;

    @Column(length = 255 * 8)
    private String label;

    @Column(length = 255 * 4)
    private String path;

    @OneToMany(mappedBy = "item", targetEntity = Sequence.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @MapKey(name = "attributeName")
    private final Map<String, Sequence> sequences = new HashMap<>();

    @CreatedDate
    private Date createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

//    public List<Node> getNodes() {
//        return Collections.unmodifiableList(nodes);
//    }

//    TODO Check if node for same branch already exists, and if so, throw exception!
//    public boolean addNode(Node node) {
//        return nodes.add(node);
//    }
//
//    public Node setNode(int index, Node node) {
//        Node replaced = nodes.set(index, node);
//
//        return replaced;
//    }

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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Locale getLocale() {
        History chronicle = getHistory();
        if (chronicle != null) {

            return chronicle.getLocale();
        }

        return null;
    }

    public boolean isCurrent() {
        History history = getHistory();
        if (history != null) {
            Item current = history.getCurrent();
            if (current != null && current.getId() != null) {
                return current.getId().equals(getId());
            }
        }

        return false;
    }

    public Item copy() {
        Item copy = new Item();
        copy.setStructure(getStructure());
        copy.setHistory(getHistory());
        copy.setLabel(getLabel());
        copy.setPath(getPath());

        Structure structure = getStructure();
        for (String attributeName : structure.getAttributeNames()) {
            Sequence sequence = getSequence(attributeName);
            if (sequence != null) {
                copy.setSequence(attributeName, sequence.copy());
            }
        }

        return copy;
    }

    @Override
    protected Map<String, Sequence> getMap() {
        return sequences;
    }
}
