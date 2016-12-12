package biz.jovido.seed.content.model;

import biz.jovido.seed.content.model.node.Fragment;
import biz.jovido.seed.content.model.node.FragmentBundle;
import biz.jovido.seed.content.model.node.Structure;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Stephan Grundner
 */
@Table(name = "node")
//@NamedEntityGraphs({
//        @NamedEntityGraph(name = "node",
//                attributeNodes = {
//                        @NamedAttributeNode(value = "fragmentMapping", subgraph = "fragments"),
//                },
//                subgraphs = {
//                        @NamedSubgraph(name = "fragments",
//                                attributeNodes = {
//                                        @NamedAttributeNode(value = "propertyMapping", subgraph = "fragment.property"),
//                                }),
//                        @NamedSubgraph(name = "fragment.property",
//                                attributeNodes = {
//                                        @NamedAttributeNode(value = "payloads"),
//                        @NamedSubgraph(name = "property.field",
//                                attributeNodes = @NamedAttributeNode(value = "field"))
//                })
//})
@Entity
public class Node implements Auditee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "structure_id", nullable = false)
    private Structure structure;

    @ManyToOne
    @JoinColumn(name = "bundle_id")
    private NodeBundle bundle;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Node parent;

    @OneToMany(mappedBy = "parent")
    @OrderColumn(name = "ordinal")
    private final List<Node> children = new ArrayList<>();

//    @Deprecated
//    @OneToMany(mappedBy = "node", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
//    @MapKeyColumn(name = "locale", nullable = false)
//    private final Map<Locale, Fragment> fragmentMapping = new HashMap<>();

    @OneToMany(mappedBy = "node", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKeyColumn(name = "locale" /*, nullable = false*/)
    private final Map<Locale, FragmentBundle> fragmentBundleMapping = new HashMap<>();

    @CreatedDate
    @Column(name = "created")
    private Date created;

    @LastModifiedDate
    @Column(name = "last_modified")
    private Date lastModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public NodeBundle getBundle() {
        return bundle;
    }

    public void setBundle(NodeBundle bundle) {
        this.bundle = bundle;

        if (!bundle.getRevisions().contains(this)) {
            bundle.addRevision(this);
        }
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildren() {
        return Collections.unmodifiableList(children);
    }

    public Set<Locale> getLocales() {
        return Collections.unmodifiableSet(fragmentBundleMapping.keySet());
    }

    public Collection<FragmentBundle> getFragmentBundles() {
        return Collections.unmodifiableCollection(fragmentBundleMapping.values());
    }

    public Collection<Fragment> getFragments() {
        return fragmentBundleMapping.values().stream()
                .map(FragmentBundle::getCurrent)
                .collect(Collectors.toList());
    }

    public FragmentBundle getFragmentBundle(Locale locale) {
        return fragmentBundleMapping.get(locale);
    }

    public Fragment getFragment(Locale locale) {
        return fragmentBundleMapping.entrySet().stream()
                .filter(e -> e.getKey() == locale)
                .map(Map.Entry::getValue)
                .map(FragmentBundle::getCurrent)
                .findFirst()
                .orElse(null);
    }

    public FragmentBundle setFragmentBundle(Locale locale, FragmentBundle fragmentBundle) {
        return fragmentBundleMapping.put(locale, fragmentBundle);
    }

    public Fragment setFragment(Locale locale, Fragment fragment) {
        FragmentBundle previous = fragmentBundleMapping.put(locale, fragment.getBundle());

        if (previous != null) {
            return previous.getCurrent();
        }

        return null;
    }

    @Override
    public Date getCreated() {
        return created;
    }

    @Override
    public Date getLastModified() {
        return lastModified;
    }
}
