package biz.jovido.seed.content;

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

    @ManyToOne(optional = false)
    private Chronicle chronicle;

    @ManyToMany(mappedBy = "targets")
    final List<Relation> relations = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @MapKey(name = "attributeName")
    private final Map<String, Payload> payloads = new HashMap<>();

    @Transient
    final ItemChangeListenerSupport listenerSupport = new ItemChangeListenerSupport(this);

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

    public List<Relation> getRelations() {
        return Collections.unmodifiableList(relations);
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
            replaced.attributeName = null;
            replaced.item = null;
            replaced.attributeName = null;
        }

        if (payload != null) {
            payload.attributeName = attributeName;
            payload.item = this;
            payload.attributeName = attributeName;
        }

        listenerSupport.notifyPayloadChange(attributeName);
//                replaced != null ? replaced.getValue() : null,
//                payload != null ? payload.getValue() : null);
    }

    public boolean addChangeListener(ItemChangeListener listener) {
        return listenerSupport.addListener(listener);
    }

    public boolean removeChangeListener(ItemChangeListener listener) {
        return listenerSupport.removeListener(listener);
    }
}
