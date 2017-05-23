package biz.jovido.seed;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@Entity
public class Organisation {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToOne(optional = true)
    private Organisation parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private final List<Organisation> children = new ArrayList<>();

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

    public Organisation getParent() {
        return parent;
    }

    protected void setParent(Organisation parent) {
        this.parent = parent;
    }

    public List<Organisation> getChildren() {
        return children;
    }

    public boolean addChild(Organisation child) {
        if (children.add(child)) {
            child.setParent(this);
            return true;
        }

        return false;
    }

    public boolean removeChild(Organisation child) {
        if (children.remove(child)) {
            child.setParent(null);
            return true;
        }

        return false;
    }
}
