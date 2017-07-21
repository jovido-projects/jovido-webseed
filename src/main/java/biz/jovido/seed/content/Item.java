package biz.jovido.seed.content;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * item.properties[title].elements[0].payloads[de-DE].value
 *
 * @author Stephan Grundner
 */
@Entity
public class Item {

    @Id
    @GeneratedValue
    private Long id;

    private String structureName;

    @OneToMany(mappedBy = "item")
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
}
