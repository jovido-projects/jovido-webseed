package biz.jovido.seed.content;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"property_id", "ordinal"}))
public class Element {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private Property property;

    @Column(name = "ordinal")
    private int index;

    @LazyCollection(LazyCollectionOption.EXTRA)
    @OneToMany(mappedBy = "element", cascade = CascadeType.ALL)
    @MapKey(name = "locale")
    private final Map<Locale, Payload> payloads = new LinkedHashMap<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Property getProperty() {
        return property;
    }

    /* public */ void setProperty(Property property) {
        this.property = property;
    }

    public int getIndex() {
        return index;
    }

    /* public */ void setIndex(int ordinal) {
        this.index = ordinal;
    }

    public Map<Locale, Payload> getPayloads() {
        return payloads;
    }

    public Payload getPayload(Locale locale) {
        Payload payload = payloads.get(locale);
        if (payload != null) {
            return payload;
        }

        return null;
    }

    public Payload setPayload(Locale locale, Payload payload) {
        Payload replaced = payloads.put(locale, payload);

        if (replaced != null) {
            replaced.setElement(null);
        }

        if (payload != null) {
            payload.setElement(this);
        }

        return replaced;
    }

    public Object getValue(Locale locale) {
        Payload payload = getPayload(locale);
        if (payload != null) {
            return payload.getValue();
        }

        return null;
    }

    public void setValue(Locale locale, Object value) {
        Payload payload = getPayload(locale);
        payload.setValue(value);
    }
}
