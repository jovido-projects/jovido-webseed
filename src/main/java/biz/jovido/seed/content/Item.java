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

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    private Item supra;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @MapKey(name = "attributeName")
    private final Map<String, Payload> payloads = new HashMap<>();

    @ManyToMany(mappedBy = "items")
    private final List<Relation> relations = new ArrayList<>();

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

    public Item getSupra() {
        return supra;
    }

    public void setSupra(Item supra) {
        this.supra = supra;
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
    }

    public Object getValue(String attributeName) {
        Payload payload = getPayloads().get(attributeName);
        if (payload != null) {
            return payload.getValue();
        }

        return null;
    }

    public void setValue(String attributeName, Object value) {
        Payload payload = getPayload(attributeName);
        payload.setValue(value);
    }
}
