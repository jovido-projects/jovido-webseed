package biz.jovido.seed.content;

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
public class Hierarchy {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "hierarchy", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @MapKey(name = "locale")
    @OrderBy("ordinal")
    @Fetch(FetchMode.SELECT)
    private final List<Node> nodes = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Node> getNodes() {
        return Collections.unmodifiableList(nodes);
    }

    public void removeNode(Node node) {
        if (nodes.remove(node)) {
            node.setHierarchy(null);
        }
    }
}
