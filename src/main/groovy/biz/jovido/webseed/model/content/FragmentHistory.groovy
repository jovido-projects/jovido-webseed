package biz.jovido.webseed.model.content

import groovy.transform.CompileStatic

import javax.persistence.*

/**
 *
 * @author Stephan Grundner
 */
@Table(name = 'fragment_history')
@Entity
@CompileStatic
class FragmentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @OneToOne
    @JoinColumns([
            @JoinColumn(name = 'current_id', referencedColumnName = 'id'),
            @JoinColumn(name = 'current_revision_id', referencedColumnName = 'revision_id')])
    Fragment current

    @OneToMany(mappedBy = 'history')
    Set<Fragment> revisions = new HashSet<>()

    Set<Fragment> getRevisions() {
        Collections.unmodifiableSet(revisions)
    }
}
