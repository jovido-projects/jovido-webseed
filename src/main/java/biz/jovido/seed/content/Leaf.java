package biz.jovido.seed.content;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@Entity
public class Leaf {

    @Id
    @GeneratedValue
    private Long id;

//    @Column(unique = true, nullable = false)
//    private String code;

//    @Column(name = "language_tag")
//    @Convert(converter = LocaleConverter.class)
//    private Locale locale;

    @OneToOne
    private Item published;

    @OneToOne(optional = false)
    private Item current;

    @OneToMany(mappedBy = "leaf", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private final List<Node> nodes = new ArrayList<>();

    @OneToMany(mappedBy = "leaf", fetch = FetchType.EAGER)
    private final List<Item> items = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public Locale getLocale() {
//        return locale;
//    }

//    public void setLocale(Locale locale) {
//        this.locale = locale;
//    }

    public Item getPublished() {
        return published;
    }

    public void setPublished(Item published) {
        this.published = published;
    }

    public Item getCurrent() {
        return current;
    }

    public void setCurrent(Item current) {
        this.current = current;
    }

    public List<Node> getNodes() {
        return Collections.unmodifiableList(nodes);
    }

    public boolean addNode(Node node) {
        if (nodes.add(node)) {
            node.setLeaf(this);
            return true;
        }

        return false;
    }

    public boolean removeNode(Node node) {
//        if (nodes.remove(node)) {
//        TODO durch idEquals o.ä. ersetzen:
        if (nodes.removeIf(it -> it.getId().equals(node.getId()))) {
            node.setLeaf(null);
            return true;
        }

        return false;
    }

    public List<Item> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Leaf)) {
            return false;
        }

        Leaf leaf = (Leaf) o;
        return new EqualsBuilder()
                .append(id, leaf.id)
                .isEquals();
    }
}
