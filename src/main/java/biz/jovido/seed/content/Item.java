package biz.jovido.seed.content;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
@Entity
public class Item {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private History history;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    @MapKey(name = "name")
    private final Map<String, Property> properties = new HashMap<>();

    public Long getId() {
        return id;
    }

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    public Map<String, Property> getProperties() {
        return Collections.unmodifiableMap(properties);
    }

    public Property getProperty(String name) {
        return properties.get(name);
    }

    public Property putProperty(Property property) {
        Property replaced = properties.put(property.getName(), property);
        property.item = this;

        return replaced;
    }
}
