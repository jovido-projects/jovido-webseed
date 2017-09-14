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

    @ManyToOne(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private Bundle bundle;

    @ManyToOne(optional = false)
    private Structure structure;

    @OneToMany(mappedBy = "item",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true)
    @MapKey(name = "attributeName")
    private final Map<String, Sequence> sequences = new HashMap<>();

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "item_node")
    private final List<Node> nodes = new ArrayList<>();

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

    /*default*/ void setId(Long id) {
        this.id = id;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
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

    public List<Node> getNodes() {
        return Collections.unmodifiableList(nodes);
    }

    public boolean addNode(Node node) {
        if (nodes.add(node)) {
//            node.setItem(this);

            return true;
        }

        return false;
    }

    public boolean removeNode(Node node) {
        if (nodes.remove(node)) {
//            node.setItem(null);

            return true;
        }

        return false;
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

    public String getLabel() {
        String labelName = getStructure().getLabelName();
        Sequence sequence = getSequence(labelName);
        Payload payload = sequence.getPayload(0);
        if (payload != null && payload instanceof TextPayload) {
            return ((TextPayload) payload).getText();
        }

        return null;
    }
}
