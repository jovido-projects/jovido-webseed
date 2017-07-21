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

    @LazyCollection(LazyCollectionOption.EXTRA)
    @ManyToOne(optional = false, targetEntity = Property.class)
    private Property property;

    private int ordinal;

    @OneToMany(mappedBy = "element")
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

    public int getOrdinal() {
        return ordinal;
    }

    /* public */ void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public Map<Locale, Payload> getPayloads() {
        return payloads;
    }
}
