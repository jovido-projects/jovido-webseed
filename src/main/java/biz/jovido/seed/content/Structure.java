package biz.jovido.seed.content;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Stephan Grundner
 */
public class Structure {

    private String name;

    private final Map<String, Attribute> attributeByFieldName = new LinkedHashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getFieldNames() {
        return attributeByFieldName.values().stream()
                .sorted(Comparator.comparingInt(Attribute::getOrdinal))
                .map(Attribute::getFieldName)
                .collect(Collectors.toSet());
    }

    public List<Attribute> getAttributes() {
        return attributeByFieldName.values().stream()
                .sorted(Comparator.comparingInt(Attribute::getOrdinal))
                .collect(Collectors.toList());
    }

    public Attribute getAttribute(String fieldName) {
        return attributeByFieldName.get(fieldName);
    }

    public Attribute putAttribute(Attribute attribute) {
        Attribute replaced = attributeByFieldName.put(attribute.getFieldName(), attribute);

        if (replaced != null) {
            replaced.setStructure(null);
        }

        attribute.setStructure(this);

        return replaced;
    }
}
