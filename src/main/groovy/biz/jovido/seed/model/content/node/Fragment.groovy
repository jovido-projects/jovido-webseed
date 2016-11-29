package biz.jovido.seed.model.content.node

import biz.jovido.seed.model.content.Node
import biz.jovido.seed.model.content.node.fragment.Property

import javax.persistence.*

/**
 *
 * @author Stephan Grundner
 */
@Table(name = 'node_fragment', uniqueConstraints =
        @UniqueConstraint(columnNames = ['locale', 'node_id']))
@Entity
class Fragment {

    @Id
    @GeneratedValue
    Long id

    @Column(name = 'locale', nullable = false)
    Locale locale

    @ManyToOne
    @JoinColumn(name = 'node_id')
    Node node

    @OneToMany(mappedBy = 'fragment')
    @MapKeyJoinColumn(name = 'field_id')
    protected final Map<Field, Property> propertyMapping = new LinkedHashMap<>()

    Set<Field> getPropertyFields() {
        Collections.unmodifiableSet(propertyMapping.keySet())
    }

    Collection<Property> getProperties() {
        Collections.unmodifiableCollection(propertyMapping.values())
    }

    Property getProperty(Field field) {
        propertyMapping.get(field)
    }

    Property addProperty(Field field, Property property) {
        propertyMapping.put(field, property)
    }
}
