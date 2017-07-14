package biz.jovido.seed.content;

import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "revision"}))
public class Structure {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;
    private int revision;

    @OneToMany(mappedBy = "structure", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @MapKey(name = "fieldName")
    @OrderBy("ordinal")
    private final Map<String, Attribute> attributeByFieldName = new HashMap<>();

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

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }

    public List<Attribute> getAttributes() {
//        TODO Sort by ordinal!
        return new ArrayList<>(attributeByFieldName.values());
    }

    public Attribute getAttribute(String fieldName) {
        return attributeByFieldName.get(fieldName);
    }

    public Attribute putAttribute(Attribute attribute) {
        String fieldName = attribute.getFieldName();
        Assert.hasText(fieldName, "[fieldName] must not be empty");
        Attribute replaced = attributeByFieldName.put(fieldName, attribute);
        if (replaced != null) {
            replaced.setStructure(null);
        }

        attribute.setStructure(this);

        return replaced;
    }
}
