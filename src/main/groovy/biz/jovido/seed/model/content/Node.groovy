package biz.jovido.seed.model.content

import biz.jovido.seed.model.Auditee
import biz.jovido.seed.model.content.node.Fragment
import biz.jovido.seed.model.content.node.Type

import javax.persistence.*

/**
 *
 * @author Stephan Grundner
 */
@Table(name = 'node', uniqueConstraints =
        @UniqueConstraint(columnNames = []))
@Entity
class Node implements Auditee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @ManyToOne
    @JoinColumn(nullable = false)
    Type type

    @ManyToOne
    @JoinColumn(name = 'parent_id')
    Node parent

    @OneToMany(mappedBy = 'parent')
    final List<Node> children = new ArrayList<>()

    List<Node> getChildren() {
        Collections.unmodifiableList(children)
    }

    @OneToMany(mappedBy = 'node')
    @MapKeyColumn(name = 'locale')
    protected final Map<Locale, Fragment> fragmentMapping = new HashMap<>()

    Set<Locale> getFragmentLocales() {
        Collections.unmodifiableSet(fragmentMapping.keySet())
    }

    Collection<Fragment> getFragments() {
        Collections.unmodifiableCollection(fragmentMapping.values())
    }

    Fragment getFragment(Locale locale) {
        fragmentMapping.get(locale)
    }
}
