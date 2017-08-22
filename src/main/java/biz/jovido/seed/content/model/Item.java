package biz.jovido.seed.content.model;

import biz.jovido.seed.security.model.User;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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

    @ManyToMany(mappedBy = "targets")
//    @Fetch(value = FetchMode.SUBSELECT)
    private final List<Relation> relations = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @MapKey(name = "attributeName")
    private final Map<String, Payload> payloads = new HashMap<>();

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

    public List<Relation> getRelations() {
        return Collections.unmodifiableList(relations);
    }

    /*default*/ void addRelation(Relation relation) {
        relations.add(relation);
    }

    public Map<String, Payload> getPayloads() {
        return Collections.unmodifiableMap(payloads);
    }

    public Payload getPayload(String attributeName) {
        return payloads.get(attributeName);
    }

    public void setPayload(String attributeName, Payload payload) {
        Payload replaced = payloads.put(attributeName, payload);
        if (replaced != null) {
            replaced.item = null;
            replaced.attributeName = null;
        }

        if (payload != null) {
            payload.item = this;
            payload.attributeName = attributeName;
        }
    }

    public Object getValue(String attributeName) {
        Payload payload = getPayload(attributeName);
        if (payload == null) {
            throw new PayloadNotFoundException(attributeName);
        }

        return payload.getValue();
    }

    public void setValue(String attributeName, Object value) {
        Payload payload = getPayload(attributeName);
        if (payload == null) {
            throw new PayloadNotFoundException(attributeName);
        }

        payload.setValue(value);
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
