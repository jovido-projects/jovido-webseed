package biz.jovido.seed.content;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
@Entity
//@Embeddable
public class Element {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private Property property;

    private int ordinal;

//    @OneToMany(mappedBy = "element")
//    @MapKey(name = "locale")
    @Transient
    private final Map<Locale, Payload> payloads = new HashMap<>();

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

    public int getOrdinal() {
        return ordinal;
    }

    /* public */ void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public Map<Locale, Payload> getPayloads() {
        return payloads;
    }

    public boolean addPayload(Payload payload) {
        payloads.put(payload.getLocale(), payload);
        payload.setElement(this);
        return true;
    }
}
