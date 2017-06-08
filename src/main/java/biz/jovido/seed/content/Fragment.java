package biz.jovido.seed.content;

import biz.jovido.seed.Domain;
import biz.jovido.seed.LocaleConverter;

import javax.persistence.*;
import java.util.*;

/**
 * @author Stephan Grundner
 */
@Entity
//@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"bundle_id", "language_tag", "revision"})})
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"bundle_id", "revision"})})
public class Fragment {

    @Id
    @GeneratedValue
    private Long id;

//    @ManyToOne(optional = false)
//    @JoinColumn(name = "bundle_id", updatable = false)
//    private Bundle bundle;
//
//    @Convert(converter = LocaleConverter.class)
//    @Column(name = "language_tag")
//    private Locale locale;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bundle_id")
    private Bundle bundle;

    private int revision;

    @OneToMany(mappedBy = "fragment", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name = "name")
    private final Map<String, Attribute> attributes = new HashMap<>();

//    @ManyToMany
//    @JoinTable(name = "domain_fragment",
//            inverseJoinColumns = @JoinColumn(name = "domain_id"),
//            joinColumns = @JoinColumn(name = "fragment_id"))
//    private final List<Domain> domains = new ArrayList<>();

//    private String path;

//    @OneToOne(mappedBy = "fragment", cascade = CascadeType.ALL)
//    private Alias alias;

//    @OneToMany(mappedBy = "fragment", cascade = CascadeType.ALL)
//    @OrderBy("domain")
//    private final Set<Alias> aliases = new HashSet<>();

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
//
//    public Locale getLocale() {
//        return locale;
//    }
//
//    public void setLocale(Locale locale) {
//        this.locale = locale;
//    }

    public Locale getLocale() {
        return bundle.getLocale();
    }

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }

    public Map<String, Attribute> getAttributes() {
        return Collections.unmodifiableMap(attributes);
    }

    public Attribute getAttribute(String name) {
        return attributes.get(name);
    }

    public void setAttribute(String name, Attribute attribute) {
        attributes.put(name, attribute);
    }

//    public List<Domain> getDomains() {
//        return Collections.unmodifiableList(domains);
//    }
//
//    public boolean addDomain(Domain domain) {
//        if (domains.add(domain)) {
//            return true;
//        }
//
//        return false;
//    }
//
//    public boolean removeDomain(Domain domain) {
//        if (domains.remove(domain)) {
//            return true;
//        }
//
//        return false;
//    }

//    public Set<Alias> getAliases() {
//        return Collections.unmodifiableSet(aliases);
//    }
//
//    public boolean addAlias(Alias alias) {
//        if (aliases.add(alias)) {
//            alias.setFragment(this);
//            return true;
//        }
//
//        return false;
//    }

//    public String getPath() {
//        return path;
//    }
//
//    public void setPath(String path) {
//        this.path = path;
//    }
}
