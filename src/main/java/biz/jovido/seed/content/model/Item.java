package biz.jovido.seed.content.model;

import javax.persistence.*;
import java.util.*;

/**
 * @author Stephan Grundner
 */
@Entity
public final class Item {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private Chronicle chronicle;

    @ManyToMany(mappedBy = "targets")
    private final List<Relation> relations = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @MapKey(name = "attributeName")
    private final Map<String, Payload> payloads = new HashMap<>();

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

}
