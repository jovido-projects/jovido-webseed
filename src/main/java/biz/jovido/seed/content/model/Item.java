package biz.jovido.seed.content.model;

import biz.jovido.seed.security.model.User;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.*;

/**
 * @author Stephan Grundner
 */
@Entity
@EntityListeners({AuditingEntityListener.class})
public final class Item {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Chronicle chronicle;

    private String structureName;

    @ManyToOne
    private Item parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private final List<Item> children = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @MapKey(name = "attributeName")
    private final Map<String, Sequence> sequences = new HashMap<>();

    @CreatedBy
    @ManyToOne(optional = false)
    private User createdBy;

    @LastModifiedBy
    @ManyToOne(optional = false)
    private User lastModifiedBy;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date lastModifiedAt;

    @OneToMany(mappedBy = "item")
    List<RelationPayload> relations = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Chronicle getChronicle() {
        return chronicle;
    }

    public void setChronicle(Chronicle chronicle) {
        this.chronicle = chronicle;
    }

    public String getStructureName() {
        return structureName;
    }

    public void setStructureName(String structureName) {
        this.structureName = structureName;
    }

    public Item getParent() {
        return parent;
    }

    public void setParent(Item parent) {
        this.parent = parent;
    }

    public List<Item> getChildren() {
        return Collections.unmodifiableList(children);
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

    public Object getValue(String attributeName, int index) {
        Sequence sequence = getSequence(attributeName);
        Assert.notNull(sequence, String.format(
                "No sequence was set for attribute [%s]", attributeName));

        List<Payload> payloads = sequence.getPayloads();
        Payload payload = payloads.get(index);
        return payload.getValue();
    }

    public Object getValue(String attributeName) {
        return getValue(attributeName, 0);
    }

    public void setValue(String attributeName, int index, Object value) {
        Sequence sequence = getSequence(attributeName);
        Assert.notNull(sequence, String.format(
                "No sequence was set for attribute [%s]", attributeName));

        List<Payload> payloads = sequence.getPayloads();
        Payload payload = payloads.get(index);
        payload.setValue(value);
    }

    public <T> void setValue(String attributeName, T value) {
        setValue(attributeName, 0, value);
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(User lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(Date lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }
}
