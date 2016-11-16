package biz.jovido.webseed.model.content

import groovy.transform.CompileStatic
import org.springframework.util.StringUtils

import javax.persistence.*

/**
 *
 * @author Stephan Grundner
 */
@Table(name = 'fragment_type', uniqueConstraints =
        @UniqueConstraint(columnNames = ['structure_id', 'name']))
@Entity
@CompileStatic
class FragmentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @ManyToOne
//    @JoinColumns([
//            @JoinColumn(name = 'structure_id', referencedColumnName = 'id'),
//            @JoinColumn(name = 'structure_revision_id', referencedColumnName = 'revision_id')])
    @JoinColumn(name = 'structure_id')
    Structure structure

    void setStructure(Structure structure) {
        assert name != null

        this.structure?.@fragmentTypes?.remove(this)

        if (structure != null) {
            structure.@fragmentTypes.put(name, this)
        }

        this.structure = structure
    }

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
        assert field != null

        field.@fragmentType = this
        fields.put(field.name, field)

        fields.eachWithIndex { Map.Entry<String, Field> entry, int i ->
            entry.value.ordinal = i
        }
    }

    void putField(String name, Constraint constraint) {
        assert !StringUtils.isEmpty(name)

        def field = new Field()
        field.name = name
        field.@constraint = constraint
//        TODO Remove existing field first

        putField(field)
    }
}
