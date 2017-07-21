package biz.jovido.seed.content;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
@Entity
public class Element {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    Property property;

    private int ordinal;

//    @OneToMany(mappedBy = "element")
//    @MapKey(name = "translation")
//    private Map<Translation, Payload> payloads = new HashMap<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Property getProperty() {
        return property;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Element)) return false;

        Element element = (Element) o;

        if (ordinal != element.ordinal) return false;
        return property != null ? property.equals(element.property) : element.property == null;
    }

    @Override
    public int hashCode() {
        int result = property != null ? property.hashCode() : 0;
        result = 31 * result + ordinal;
        return result;
    }
}
