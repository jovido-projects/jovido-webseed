package biz.jovido.seed.content;

import javax.persistence.*;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Stephan Grundner
 */
//@Entity
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name", "revision"}))
public class Structure {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private boolean nestedOnly;
    private boolean publishable;

    private String labelAttributeName;

    private String template;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNestedOnly() {
        return nestedOnly;
    }

    public void setNestedOnly(boolean nestedOnly) {
        this.nestedOnly = nestedOnly;
    }

    public boolean isPublishable() {
        return publishable;
    }

    public void setPublishable(boolean publishable) {
        this.publishable = publishable;
    }

    public String getLabelAttributeName() {
        return labelAttributeName;
    }

    public void setLabelAttributeName(String labelAttributeName) {
        this.labelAttributeName = labelAttributeName;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
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

    public Attribute getLabelAttribute() {
        String attributeName = getLabelAttributeName();
        return getAttribute(attributeName);
    }

    public List<Attribute> getAttributes() {
        return attributes.values().stream()
                .sorted(Comparator.comparingInt(Attribute::getOrdinal))
                .collect(Collectors.toList());
    }
}
