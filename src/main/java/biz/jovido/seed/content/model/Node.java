package biz.jovido.seed.content.model;

import biz.jovido.seed.content.model.node.Bundle;
import biz.jovido.seed.content.model.node.Fragment;
import biz.jovido.seed.content.model.node.Type;

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
public class Node {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Type type;

    @ManyToOne
    @JoinColumn(name = "bundle_id")
    private Bundle bundle;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Node parent;

    @OneToMany(mappedBy = "parent")
    private final List<Node> children = new ArrayList<>();

    @OneToMany(mappedBy = "node", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKeyColumn(name = "locale")
    private final Map<Locale, Fragment> fragmentMapping = new HashMap<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
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
}
