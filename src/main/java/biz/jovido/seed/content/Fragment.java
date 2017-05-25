package biz.jovido.seed.content;

import biz.jovido.seed.LocaleConverter;

import javax.persistence.*;
import java.util.*;

/**
 * @author Stephan Grundner
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"bundle_id", "revision", "language_tag"}))
public class Fragment {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bundle_id", updatable = false)
    private Bundle bundle;

    @Column(updatable = false)
    private int revision;

    @Convert(converter = LocaleConverter.class)
    @Column(name = "language_tag")
    private Locale locale;

    @OneToMany(mappedBy = "fragment", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name = "name")
    private final Map<String, Attribute> attributes = new HashMap<>();

//    @OneToOne(mappedBy = "fragment", cascade = CascadeType.ALL)
//    private Alias alias;

    @OneToMany(mappedBy = "fragment", cascade = CascadeType.ALL)
    private final Set<Alias> aliases = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Map<String, Attribute> getAttributes() {
        return attributes;
    }

    public Attribute getAttribute(String name) {
        return attributes.get(name);
    }

    public void setAttribute(String name, Attribute attribute) {
        attributes.put(name, attribute);
    }

    public Set<Alias> getAliases() {
        return Collections.unmodifiableSet(aliases);
    }

    public boolean addAlias(Alias alias) {
        if (aliases.add(alias)) {
            alias.setFragment(this);
            return true;
        }

        return false;
    }
}
