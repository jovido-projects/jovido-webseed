package biz.jovido.seed.content.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"item_id", "name"}))
public final class Property {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    Item item;
    String name;

    @OneToMany(mappedBy = "property")
    @OrderBy("ordinal")
    private final List<Payload> payloads = new ArrayList<>();

    @Transient
    private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public String getName() {
        return name;
    }

    public List<Payload> getPayloads() {
        return Collections.unmodifiableList(payloads);
    }

    public Payload getPayload(int index) {
        return payloads.get(index);
    }

    public boolean addPayload(Payload payload) {
        if (payloads.add(payload)) {
            payload.property = this;
            changeSupport.notifyPayloadAdded(payload);
            return true;
        }

        return false;
    }

    public boolean removePayload(int index) {
        Payload payload = payloads.remove(index);
        if (payload != null) {
            payload.property = null;
            changeSupport.notifyPayloadRemoved(payload, index);
            return true;
        }

        return false;
    }

    public boolean removePayload(Payload payload) {
        int index = payloads.indexOf(payload);
        return removePayload(index);
    }



    public boolean addChangeListener(PropertyChangeListener listener) {
        return changeSupport.addListener(listener);
    }

    public boolean removeChangeListener(PropertyChangeListener listener) {
        return changeSupport.removeListener(listener);
    }
}
