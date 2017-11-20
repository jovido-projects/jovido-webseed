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

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @MapKeyColumn(name = "attribute_name")
    private final Map<String, PayloadGroup> payloadGroups = new HashMap<>();

    @Column(name = "language_tag")
    @Convert(converter = LocaleConverter.class)
    private Locale locale;

    @Column(length = 255 * 4)
    private String path;

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

    public Set<String> getAttributeNames() {
        return Collections.unmodifiableSet(payloadGroups.keySet());
    }

    public Map<String, PayloadGroup> getPayloadGroups() {
        return Collections.unmodifiableMap(payloadGroups);
    }

    public PayloadGroup getPayloadGroup(String attributeName) {
        return payloadGroups.get(attributeName);
    }

    public void setPayloadGroup(String attributeName, PayloadGroup payloadGroup) {
//        TODO Sauber implementieren:
        payloadGroup.setItem(this);
        payloadGroup.setAttributeName(attributeName);
        payloadGroups.put(attributeName, payloadGroup);
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

        for (String attributeName : getAttributeNames()) {
            PayloadGroup payloadGroup = getPayloadGroup(attributeName);
            copy.setPayloadGroup(attributeName, payloadGroup.copy());
        }

        return copy;
    }
}
