package biz.jovido.seed.model.content.node

import javax.persistence.*

/**
 *
 * @author Stephan Grundner
 */
@Table(name = 'node_type')
@Entity
class Type {

    @Id
    @GeneratedValue
    Long id

    @Column(unique = true)
    String name

    @OneToMany(mappedBy = 'type', cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @MapKeyColumn(name = 'name')
    @OrderColumn(name = 'ordinal')
    protected final Map<String, Field> fieldMapping = new LinkedHashMap<>()

    Set<String> getFieldNames() {
        Collections.unmodifiableSet(fieldMapping.keySet())
    }

    Collection<Field> getFields() {
        Collections.unmodifiableCollection(fieldMapping.values())
    }

    Field getField(String fieldName) {
        fieldMapping.get(fieldName)
    }

    Field addField(Field field) {
        fieldMapping.put(field.name, field)
    }
}
