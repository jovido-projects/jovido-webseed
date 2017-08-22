package biz.jovido.seed.content.model;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Stephan Grundner
 */
public class Structure {

    private String name;
    private boolean standalone;

    private final Map<String, Attribute> attributes = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStandalone() {
        return standalone;
    }

    public void setStandalone(boolean standalone) {
        this.standalone = standalone;
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
