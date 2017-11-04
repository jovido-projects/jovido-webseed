package biz.jovido.seed.content;

import biz.jovido.seed.LocaleConverter;
import biz.jovido.seed.security.User;
import biz.jovido.seed.util.UnmodifiableMapProxy;
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
@EntityListeners(AuditingEntityListener.class)
public class Item extends UnmodifiableMapProxy<String, Sequence> {

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

    @OneToMany(mappedBy = "item", targetEntity = Sequence.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @MapKey(name = "attributeName")
    private final Map<String, Sequence> sequences = new HashMap<>();

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date lastModifiedAt;

    @CreatedBy
    private User createdBy;

    @LastModifiedBy
    private User lastModifiedBy;

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

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

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

    public Date getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(Date lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
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

//    public Locale getLocale() {
//        Leaf chronicle = getLeaf();
//        if (chronicle != null) {
//
//            return chronicle.getLocale();
//        }
//
//        return null;
//    }

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

    public boolean belongsTo(Leaf leaf) {
        if (leaf != null) {

            if (leaf.equals(this.leaf)) {
                return true;
            }
        }

        return false;
    }

    @Override
    protected Map<String, Sequence> getMap() {
        return sequences;
    }
}
