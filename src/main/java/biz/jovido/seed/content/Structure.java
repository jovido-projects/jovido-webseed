package biz.jovido.seed.content;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Stephan Grundner
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "revision"}))
public class Structure {

    @Id
    @GeneratedValue
    private Long id;

    @Column(updatable = false)
    private String name;

    @Column(updatable = false)
    private int revision;

    @OneToMany(mappedBy = "structure",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private final List<Attribute> attributes = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }

    public Set<String> getAttributeNames() {
        return attributes.stream()
                .map(Attribute::getName)
                .collect(Collectors.toSet());
    }

    public List<Attribute> getAttributes() {
        return Collections.unmodifiableList(attributes);
    }

    public Attribute getAttribute(String name) {
        return attributes.stream()
                .filter(attribute -> attribute.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public boolean addAttribute(Attribute attribute) {
        if (attributes.add(attribute)) {
            attribute.setStructure(this);
            return true;
        }
        return false;
    }

    public Attribute removeAttribute(int index) {
        Attribute removed = attributes.remove(index);
        if (removed != null) {
            removed.setStructure(null);
        }
        return removed;
    }
}
