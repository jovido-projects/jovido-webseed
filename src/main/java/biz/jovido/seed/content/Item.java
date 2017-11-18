package biz.jovido.seed.content;

import biz.jovido.seed.AbstractUnique;
import biz.jovido.seed.LocaleConverter;
import biz.jovido.seed.security.User;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Stephan Grundner
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Item extends AbstractUnique {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Leaf leaf;

    private String structureName;

    @Column(name = "language_tag")
    @Convert(converter = LocaleConverter.class)
    private Locale locale;

    @Column(length = 255 * 4)
    private String path;

    @OneToMany(mappedBy = "owningItem", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private final Set<Payload> payloads = new LinkedHashSet<>();

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date lastModifiedAt;

    @CreatedBy
    @ManyToOne
    private User createdBy;

    @LastModifiedBy
    @ManyToOne
    private User lastModifiedBy;

    @Transient
    private final Set<ItemChangeListener> changeListeners = new LinkedHashSet<>();

    public Long getId() {
        return id;
    }

    /*public*/ void setId(Long id) {
        this.id = id;
    }

    public Leaf getLeaf() {
        return leaf;
    }

    /*public*/ void setLeaf(Leaf leaf) {
        this.leaf = leaf;
    }

    public String getStructureName() {
        return structureName;
    }

    /*public*/ void setStructureName(String structureName) {
        this.structureName = structureName;
    }

    public Locale getLocale() {
        return locale;
    }

    /*public*/ void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Set<Payload> getPayloads() {
        return Collections.unmodifiableSet(payloads);
    }

    @Deprecated
    public Set<String> getAttributeNames() {
        return payloads.stream()
                .map(Payload::getAttributeName)
                .distinct()
                .collect(Collectors.toSet());
    }

    public boolean addPayload(Payload payload) {
        Assert.hasText(payload.getAttributeName(), "[payload.attributeName] must not be empty");
        if (payloads.add(payload)) {
            payload.setOwningItem(this);
            long size = payloads.stream()
                    .filter(it -> it.getAttributeName().equals(payload.getAttributeName()))
                    .count();
            payload.setOrdinal((int) size - 1);

            for (ItemChangeListener changeListener : getChangeListeners()) {
                changeListener.payloadAdded(this, payload);
            }

            return true;
        }

        return false;
    }

    public boolean removePayload(Payload payload) {
        if (payloads.remove(payload)) {
            for (ItemChangeListener changeListener : getChangeListeners()) {
                changeListener.payloadRemoved(this, payload);
            }

            payload.setOwningItem(null);
            payload.setOrdinal(-1);

            ListIterator<Payload> listIterator = ItemUtils
                    .getPayloads(this, payload.getAttributeName()).listIterator();
            while (listIterator.hasNext()) {
                Payload next = listIterator.next();
                next.setOrdinal(listIterator.previousIndex());
            }

            return true;
        }

        return false;
    }

//    public void movePayload(String attributeName, int fromIndex, int toIndex) {
//        List<Payload> payloads = ItemUtils.getPayloads(this, attributeName);
//        Payload
//    }

    public Date getCreatedAt() {
        return createdAt;
    }

    /*public*/ void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getLastModifiedAt() {
        return lastModifiedAt;
    }

    /*public*/ void setLastModifiedAt(Date lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    /*public*/ void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getLastModifiedBy() {
        return lastModifiedBy;
    }

    /*public*/ void setLastModifiedBy(User lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
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

    @Deprecated
    public boolean belongsTo(Leaf leaf) {
        if (leaf != null) {

            if (leaf.equals(this.leaf)) {
                return true;
            }
        }

        return false;
    }

    public Set<ItemChangeListener> getChangeListeners() {
        return Collections.unmodifiableSet(changeListeners);
    }

    public boolean addChangeListener(ItemChangeListener changeListener) {
        return changeListeners.add(changeListener);
    }

    public boolean removeChangeListener(ItemChangeListener changeListener) {
        return changeListeners.remove(changeListener);
    }

    public boolean isPublished() {
        Leaf leaf = getLeaf();
        if (leaf != null) {
            return leaf.getPublished() != null;
        }

        return false;
    }

    public Item copy() {
        Item copy = new Item();
        copy.setLeaf(getLeaf());
        copy.setStructureName(getStructureName());
        copy.setLocale(getLocale());
        copy.setPath(getPath());

        copy.setCreatedAt(getCreatedAt());
        copy.setLastModifiedAt(getLastModifiedAt());
        copy.setCreatedBy(getCreatedBy());
        copy.setLastModifiedBy(getLastModifiedBy());

        for (Payload payload : payloads) {
            copy.addPayload(payload.copy());
        }

        return copy;
    }
}
