package biz.jovido.seed.content;

import biz.jovido.seed.LocaleConverter;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Stephan Grundner
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"hierarchy_id", "language_tag"}))
public class Branch {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "hierarchy_id")
    private Hierarchy hierarchy;

    @Column(name = "language_tag")
    @Convert(converter = LocaleConverter.class)
    private Locale locale;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private final List<Node> nodes = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Hierarchy getHierarchy() {
        return hierarchy;
    }

    /*public*/ void setHierarchy(Hierarchy hierarchy) {
        this.hierarchy = hierarchy;
    }

    public Locale getLocale() {
        return locale;
    }

    /*public*/ void setLocale(Locale locale) {
        this.locale = locale;
    }

    public List<Node> getNodes() {
        return Collections.unmodifiableList(nodes);
    }

    public void addNode(Node node) {
        if (nodes.add(node)) {
            node.setBranch(this);
        }
    }

    public List<Node> getRoots() {
        return nodes.stream()
                .filter(it -> it.getParent() == null)
                .collect(Collectors.toList());
    }
}
