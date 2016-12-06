package biz.jovido.seed.content.model;

import biz.jovido.seed.content.model.node.Bundle;
import biz.jovido.seed.content.model.node.Fragment;
import biz.jovido.seed.content.model.node.Structure;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.*;

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
    private Bundle bundle;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Node parent;

    @OneToMany(mappedBy = "parent")
    @OrderColumn(name = "ordinal")
    private final List<Node> children = new ArrayList<>();

    @OneToMany(mappedBy = "node", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKeyColumn(name = "locale", nullable = false)
    private final Map<Locale, Fragment> fragmentMapping = new HashMap<>();

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

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;

        if (!bundle.getNodes().contains(this)) {
            bundle.addNode(this);
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

    public Set<Locale> getFragmentLocales() {
        return Collections.unmodifiableSet(fragmentMapping.keySet());
    }

    public Collection<Fragment> getFragments() {
        return Collections.unmodifiableCollection(fragmentMapping.values());
    }

    public Fragment getFragment(Locale locale) {
        return fragmentMapping.get(locale);
    }

    public Fragment setFragment(Locale locale, Fragment fragment) {
        return fragmentMapping.put(locale, fragment);
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
