package biz.jovido.seed.content;

import biz.jovido.seed.util.UnmodifiableMapProxy;
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
    private Leaf leaf;

    private String structureName;

//    @ManyToOne(optional = false)
//    private Structure structure;

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

    public Leaf getLeaf() {
        return leaf;
    }

    public void setLeaf(Leaf leaf) {
        this.leaf = leaf;
    }

    public String getStructureName() {
        return structureName;
    }

    public void setStructureName(String structureName) {
        this.structureName = structureName;
    }
//    public Structure getStructure() {
//        return structure;
//    }
//
//    public void setStructure(Structure structure) {
//        this.structure = structure;
//    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, Sequence> getSequences() {
        return Collections.unmodifiableMap(sequences);
    }

    public Sequence getSequence(String attributeName) {
        Sequence sequence = sequences.get(attributeName);

        return sequence;
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
        Leaf chronicle = getLeaf();
        if (chronicle != null) {

            return chronicle.getLocale();
        }

        return null;
    }

    public boolean isCurrent() {
        Leaf history = getLeaf();
        if (history != null) {
            Item current = history.getCurrent();
            if (current != null && current.getId() != null) {
                return current.getId().equals(getId());
            }
        }

        return false;
    }

//    public Item copy() {
//        Item copy = new Item();
////        copy.setStructure(getStructure());
//        copy.setLeaf(getLeaf());
//        copy.setPath(getPath());
//
//        Structure structure = getStructure();
//        for (String attributeName : structure.getAttributeNames()) {
//            Sequence sequence = getSequence(attributeName);
//            if (sequence != null) {
//                copy.setSequence(attributeName, sequence.copy());
//            }
//        }
//
//        for (Sequence sequence : getSequences().values()) {
//            copy.setSequence(sequence.getAttributeName(), sequence.copy());
//        }
//
//        return copy;
//    }

    @Override
    protected Map<String, Sequence> getMap() {
        return sequences;
    }
}
