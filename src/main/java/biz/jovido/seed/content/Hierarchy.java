package biz.jovido.seed.content;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
//    @OrderBy("ordinal")
    @Fetch(FetchMode.SUBSELECT)
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

    public List<Node> getRootNodes() {
        return Collections.unmodifiableList(nodes).stream()
                .filter(Node::isRoot)
                .sorted(Comparator.comparingInt(Node::getOrdinal))
                .collect(Collectors.toList());
    }

    private void updateOrdinalsOnRootNodes() {
        List<Node> rootNodes = getRootNodes();
        for (int i = 0; i < rootNodes.size(); i++) {
            Node node = rootNodes.get(i);
            node.setOrdinal(i);
        }
    }

    public boolean addNode(Node node) {
        if (nodes.add(node)) {
            node.setHierarchy(this);

            if (node.isRoot()) {
                updateOrdinalsOnRootNodes();
                List<Node> rootNodes = getRootNodes();
                node.setOrdinal(rootNodes.size());
            }

            return true;
        }

        return false;
    }

    public void removeNode(Node node) {
        if (nodes.remove(node)) {
            node.setHierarchy(null);
            if (node.isRoot()) {
                updateOrdinalsOnRootNodes();
            }
        }
    }
}
