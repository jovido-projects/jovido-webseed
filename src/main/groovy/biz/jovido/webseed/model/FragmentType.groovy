package biz.jovido.webseed.model

import groovy.transform.CompileStatic

import javax.persistence.*

/**
 *
 * @author Stephan Grundner
 */
@Table(name = 'fragment_type', uniqueConstraints =
        @UniqueConstraint(columnNames = ['structure_id', 'structure_revision_id', 'name']))
@Entity
@CompileStatic
class FragmentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @ManyToOne
    @JoinColumns([
            @JoinColumn(name = 'structure_id', referencedColumnName = 'id'),
            @JoinColumn(name = 'structure_revision_id', referencedColumnName = 'revision_id')])
    Structure structure

    @Column(nullable = false, updatable = false)
    String name

    @OneToMany(mappedBy = 'fragmentType',
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true)
    @MapKey(name = 'name')
    @OrderColumn(name = 'ordinal')
    final Map<String, Field> fields = new LinkedHashMap<>()

    Field getField(String fieldName) {
        fields.get(fieldName)
    }

    void putField(Field field) {
        def fieldName = field.name
//        TODO Remove existing field first
        fields.put(fieldName, field)
    }

    void putField(String name, Constraint constraint) {
        def field = new Field()
        field.name = name
        field.@constraint = constraint
        field.@fragmentType = this
//        TODO Remove existing field first
        fields.put(name, field)
    }
}
