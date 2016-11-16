package biz.jovido.webseed.model.content

import javax.persistence.*

/**
 *
 * @author Stephan Grundner
 */
@Table(name = 'structure')
@Entity
class Structure implements Serializable {

    @Id
    @GeneratedValue
    Long id

    @Column(name = 'revision_id', unique = true)
    String revisionId

    @OneToMany(mappedBy = 'structure', cascade = CascadeType.ALL)
    @MapKey(name = 'name')
    Map<String, Constraint> constraints = new HashMap<>()

    Map<String, Constraint> getConstraints() {
        Collections.unmodifiableMap(constraints)
    }

    Constraint getConstraint(String constraintName) {
        constraints.get(constraintName)
    }

    void putConstraint(Constraint constraint) {
        constraint.structure = this
    }

    @OneToMany(mappedBy = 'structure', cascade = CascadeType.ALL)
    @MapKey(name = 'name')
    Map<String, FragmentType> fragmentTypes = new HashMap<>()

    Map<String, FragmentType> getFragmentTypes() {
        Collections.unmodifiableMap(fragmentTypes)
    }

    FragmentType getFragmentType(String fragmentTypeName) {
        fragmentTypes.get(fragmentTypeName)
    }

    void putFragmentType(FragmentType fragmentType) {
        fragmentType.structure = this
    }
}
