package biz.jovido.webseed.model.content

import org.springframework.util.StringUtils

import javax.persistence.*

/**
 *
 * @author Stephan Grundner
 */
@Table(name = 'field_group', uniqueConstraints =
        @UniqueConstraint(columnNames = ['fragment_type_id', 'name']))
@Entity
class FieldGroup {

    @Id
    @GeneratedValue
    Long id

    @ManyToOne
    @JoinColumn(name = 'fragment_type_id', nullable = false, updatable = false)
    FragmentType fragmentType

    void setFragmentType(FragmentType fragmentType) {
        this.fragmentType?.@fieldGroups?.remove(name)

        if (fragmentType != null && !StringUtils.isEmpty(name)) {
            fragmentType.putFieldGroup(this)
        }

        this.fragmentType = fragmentType
    }

    @OneToMany(mappedBy = 'group',
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

        field.@group = this
        fields.put(field.name, field)

        fields.eachWithIndex { Map.Entry<String, Field> entry, int i ->
            entry.value.ordinal = i
        }
    }

    @Column(nullable = false, updatable = false)
    String name

    void setName(String name) {
        this.name = name

        if (fragmentType != null) {
            fragmentType?.@fieldGroups?.put(name, this)
        }
    }
}
