package biz.jovido.seed.content.model;

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

    @ManyToOne(optional = false,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "type_id")
    private Type type;
    private int revision;

    private boolean standalone;
    private String labelName;

    @OneToMany(mappedBy = "structure",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @MapKey(name = "name")
    @OrderBy("ordinal")
    private final Map<String, Attribute> attributes = new LinkedHashMap<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "accepted_hierarchy",
            uniqueConstraints = @UniqueConstraint(columnNames = {"structure_id", "name"}),
            joinColumns = @JoinColumn(name = "structure_id"))
    @Column(name = "name")
    private final Set<String> acceptedHierarchyNames = new LinkedHashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    /*default*/ void setType(Type type) {
        this.type = type;
    }

    public int getRevision() {
        return revision;
    }

    /*default*/ void setRevision(int revision) {
        this.revision = revision;
    }

    public boolean isStandalone() {
        return standalone;
    }

    public void setStandalone(boolean standalone) {
        this.standalone = standalone;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
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

    public Set<String> getAcceptedHierarchyNames() {
        return acceptedHierarchyNames;
    }
}
