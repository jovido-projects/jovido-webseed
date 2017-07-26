package biz.jovido.seed.content;

import javax.persistence.*;
import java.util.*;

/**
 * @author Stephan Grundner
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"bundle_id", "locale"}))
public class Item {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Bundle bundle;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private History history;

    @Column(nullable = false)
    private Locale locale;

    @ManyToMany(mappedBy = "targets")
    private final List<RelationPayload> relations = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    @MapKey(name = "attributeName")
    private final Map<String, Payload> payloads = new HashMap<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bundle getBundle() {
        return bundle;
    }

    /* public */ void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public List<RelationPayload> getRelations() {
        return Collections.unmodifiableList(relations);
    }

    public void addRelation(RelationPayload relation) {
        relations.add(relation);
    }

    public Map<String, Payload> getPayloads() {
        return Collections.unmodifiableMap(payloads);
    }

    public Payload getPayload(String attributeName) {
        return payloads.get(attributeName);
    }

    public void setPayload(String attributeName, Payload payload) {
        if (payload != null) {
            payload.setAttributeName(attributeName);
            payload.setItem(this);
        }

        Payload replaced = payloads.put(attributeName, payload);
        if (replaced != null) {
            replaced.setAttributeName(null);
            replaced.setItem(null);
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
        Payload payload = getPayloads().get(attributeName);
        payload.setValue(value);
    }
}
