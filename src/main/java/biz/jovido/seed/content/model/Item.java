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

    @OneToMany(mappedBy = "target")
    private final List<Relation> relations = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @MapKey(name = "name")
    private final Map<String, Property> properties = new HashMap<>();

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

    public Map<String, Property> getProperties() {
        return Collections.unmodifiableMap(properties);
    }

    public Property getProperty(String name) {
        return properties.get(name);
    }

    public void setProperty(String name, Property property) {
        Property replaced = properties.put(name, property);
        if (replaced != null) {
            replaced.item = null;
            replaced.name = null;
        }

        if (property != null) {
            property.item = this;
            property.name = name;
        }
    }
}
