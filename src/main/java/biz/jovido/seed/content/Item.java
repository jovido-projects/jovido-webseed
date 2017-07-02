package biz.jovido.seed.content;

import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.*;

/**
 * @author Stephan Grundner
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"bundle_id", "revision"}))
public class Item {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Bundle bundle;

    private int revision;

    private String path;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @MapKey(name = "name")
    private final Map<String, Property<?>> propertyByName = new HashMap<>();

    public Long getId() {
        return id;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        if (StringUtils.hasLength(path)) {
            if (!path.startsWith("/")) {
                path = String.format("/%s", path);
            }
        }

        this.path = path;
    }

    public Set<String> getPropertyNames() {
        return Collections.unmodifiableSet(propertyByName.keySet());
    }

    public List<Property<?>> getProperties() {
        List<Property<?>> properties = new ArrayList<>();
        for (String name : getBundle().getStructure().getAttributeNames()) {
            properties.add(getProperty(name));
        }

        return Collections.unmodifiableList(properties);
    }

    public Property<?> getProperty(String name) {
        return propertyByName.get(name);
    }

    public Property<?> putProperty(Property<?> property) {
        Property<?> replaced = propertyByName.put(property.getName(), property);

        if (replaced != null) {
            replaced.setItem(null);
        }

        property.setItem(this);

        return replaced;
    }

    @SuppressWarnings("unchecked")
    public <T> T getValue(String propertyName, int index) {
        Property<T> property = (Property<T>) getProperty(propertyName);
        return property.getValue(index);
    }

    @SuppressWarnings("unchecked")
    public <T> T setValue(String propertyName, int index, T value) {
        Property<T> property = (Property<T>) getProperty(propertyName);
        return property.setValue(index, value);
    }

    public <T> T getValue(String propertyName) {
        return getValue(propertyName, 0);
    }

    public <T> T setValue(String propertyName, T value) {
        return setValue(propertyName, 0, value);
    }
}
