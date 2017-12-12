package biz.jovido.seed.content.structure;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Stephan Grundner
 */
public class Structure {

    private final String name;
    private boolean publishable;

    private final Map<String, Attribute> attributeByName = new LinkedHashMap<>();

    public String getName() {
        return name;
    }

    public boolean isPublishable() {
        return publishable;
    }

    public void setPublishable(boolean publishable) {
        this.publishable = publishable;
    }

    public Set<String> getAttributeNames() {
        return Collections.unmodifiableSet(attributeByName.keySet());
    }

    public List<Attribute> getAttributes() {
        List<Attribute> attributes = attributeByName.values().stream()
                .sorted(Comparator.comparingInt(Attribute::getOrdinal))
                .collect(Collectors.toList());
        return Collections.unmodifiableList(attributes);
    }

    public Attribute getAttribute(String name) {
        return attributeByName.get(name);
    }

    public Attribute setAttribute(String name, Attribute attribute) {
        if (attribute != null) {
            attribute.setStructure(this);
            attribute.setName(name);
            attribute.setOrdinal(attributeByName.size());
        }

        Attribute replaced = attributeByName.put(name, attribute);
        if (replaced != null) {
            replaced.setStructure(null);
            replaced.setName(null);
            replaced.setOrdinal(-1);
        }

        return replaced;
    }

    public Structure(String name) {
        this.name = name;
    }
}
