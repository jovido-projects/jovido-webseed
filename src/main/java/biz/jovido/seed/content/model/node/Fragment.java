package biz.jovido.seed.content.model.node;

import biz.jovido.seed.content.converter.LocaleToLanguageTagConverter;
import biz.jovido.seed.content.model.Node;
import biz.jovido.seed.content.model.node.fragment.Property;

import javax.persistence.*;
import java.util.*;

/**
 * @author Stephan Grundner
 */
@Table(name = "node_fragment", uniqueConstraints = @UniqueConstraint(columnNames = {"locale", "node_id"}))
@Entity
public class Fragment {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "locale", nullable = false)
    @Convert(converter = LocaleToLanguageTagConverter.class)
    private Locale locale;

    @ManyToOne
    @JoinColumn(name = "node_id")
    private Node node;

    @OneToMany(mappedBy = "fragment", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKeyJoinColumn(name = "field_id")
    private final Map<Field, Property> propertyMapping = new LinkedHashMap<Field, Property>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Set<Field> getPropertyFields() {
        return Collections.unmodifiableSet(propertyMapping.keySet());
    }

    public Collection<Property> getProperties() {
        return Collections.unmodifiableCollection(propertyMapping.values());
    }

    public Property getProperty(Field field) {
        return propertyMapping.get(field);
    }

    public Property getProperty(String fieldName) {
        Type type = node.getType();
        Field field = type.getField(fieldName);

        return getProperty(field);
    }

    @Deprecated
    public Property addProperty(Field field, Property property) {
        return propertyMapping.put(field, property);
    }

    public Property addProperty(Field field, int size) {
        Property property = new Property();
        property.setField(field);
        property.setFragment(this);
        property.setSize(size);
        return propertyMapping.put(field, property);
    }

    public Property addProperty(Field field) {
        return addProperty(field, 0);
    }
}
