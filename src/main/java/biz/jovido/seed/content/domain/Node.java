package biz.jovido.seed.content.domain;

import javax.persistence.*;
import java.util.*;

/**
 * @author Stephan Grundner
 */
@Entity
public class Node {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Node parent;

    @OneToMany(mappedBy = "parent")
    private final Set<Node> children = new LinkedHashSet<>();

    @OneToMany
    @JoinTable(name = "node_fragment")
    private final Map<Locale, Fragment> fragmentMapping = new HashMap<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Locale> getLocales() {
        return Collections.unmodifiableSet(fragmentMapping.keySet());
    }

    public Collection<Fragment> getFragments() {
        return Collections.unmodifiableCollection(fragmentMapping.values());
    }

    public Fragment setFragment(Locale locale, Fragment fragment) {
        return fragmentMapping.put(locale, fragment);
    }

    public Fragment getFragment(Locale locale) {
        return fragmentMapping.get(locale);
    }
}
