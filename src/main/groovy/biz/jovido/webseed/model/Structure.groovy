package biz.jovido.webseed.model

import javax.persistence.*

/**
 *
 * @author Stephan Grundner
 */
@Table(name = 'structure')
@Entity
class Structure {

    @Id
    Long id

    @Column(name = 'revision_id', unique = true)
    String revisionId

    @OneToMany(mappedBy = 'structure')
    @MapKey(name = 'name')
    Map<String, Constraint> constraints = new HashMap<>()

    @OneToMany(mappedBy = 'structure')
    @MapKey(name = 'name')
    Map<String, FragmentType> fragmentTypes = new HashMap<>()
}
