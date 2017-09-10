package biz.jovido.seed.content.model;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
@Entity
public class Hierarchy {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "hierarchy",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @MapKey(name = "locale")
    private final Map<Locale, Root> roots = new HashMap<>();

    public Long getId() {
        return id;
    }

    /*default*/ void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Locale, Root> getRoots() {
        return Collections.unmodifiableMap(roots);
    }

    public Root getRoot(Locale locale) {
        return roots.get(locale);
    }

    public Root setRoot(Locale locale, Root root) {
        Root replaced = roots.put(locale, root);
        if (replaced != null) {
            replaced.setHierarchy(null);
            replaced.setLocale(null);
        }

        if (root != null) {
            root.setHierarchy(this);
            root.setLocale(locale);
        }

        return replaced;
    }
}