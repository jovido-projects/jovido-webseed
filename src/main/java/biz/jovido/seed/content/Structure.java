package biz.jovido.seed.content;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Stephan Grundner
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"type_id", "revision"}))
public class Structure {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "type_id", updatable = false)
    private Type type;

    @Column(updatable = false)
    private int revision;

    private boolean publishable;

    @OneToMany(mappedBy = "structure", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @MapKey(name = "name")
    @OrderBy("ordinal")
    private final Map<String, Attribute> attributes = new LinkedHashMap<>();

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

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }

    public boolean isPublishable() {
        return publishable;
    }

    public void setPublishable(boolean publishable) {
        this.publishable = publishable;
    }

    public List<String> getAttributeNames() {
        return attributes.values().stream()
                .sorted(Comparator.comparingInt(Attribute::getOrdinal))
                .map(Attribute::getName)
                .collect(Collectors.toList());
    }

    public Attribute getAttribute(String name) {
        return attributes.get(name);
    }

    public Attribute setAttribute(String name, Attribute attribute) {
        Attribute replaced = attributes.put(name, attribute);
        if (replaced != null) {
            replaced.setName(null);
            replaced.setStructure(null);
        }

        attribute.setName(name);
        attribute.setStructure(this);

        return replaced;
    }

    public List<Attribute> getAttributes() {
        return attributes.values().stream()
                .sorted(Comparator.comparingInt(Attribute::getOrdinal))
                .collect(Collectors.toList());
    }
}
