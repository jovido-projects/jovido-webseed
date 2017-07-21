package biz.jovido.seed.content;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.*;

/**
 * item.properties[title].elements[0].value
 *
 * @author Stephan Grundner
 */
@Entity
public class Item {

    @Id
    @GeneratedValue
    private Long id;

    private String structureName;

    @ManyToOne
    private History history;

    @OneToOne
    private ItemPayload referrer;

    @CreatedDate
    private Date created;

    private String label;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
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

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    public ItemPayload getReferrer() {
        return referrer;
    }

    public void setReferrer(ItemPayload referrer) {
        this.referrer = referrer;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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
            replaced.setItem(null);
            replaced.setName(null);
        }

        if (property != null) {
            property.setItem(this);
            property.setName(name);
        }
    }
}
