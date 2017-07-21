package biz.jovido.seed.content;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * item.properties[title].elements[0].value
 *
 * @author Stephan Grundner
 */
@Entity
public class Chunk {

    @Id
    @GeneratedValue
    private Long id;

    private String structureName;

    @ManyToOne
    private Item item;

    @OneToOne
    private Payload referrer;

    @OneToMany(mappedBy = "chunk", targetEntity = Property.class)
    @MapKey(name = "name")
    private final Map<String, Property> properties = new HashMap<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStructureName() {
        return structureName;
    }

    public void setStructureName(String structureName) {
        this.structureName = structureName;
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
            replaced.setChunk(null);
            replaced.setName(null);
        }

        if (property != null) {
            property.setChunk(this);
            property.setName(name);
        }
    }
}
