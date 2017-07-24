package biz.jovido.seed.content;

import org.springframework.util.Assert;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Stephan Grundner
 */
public class Structure {

    private final String name;
    private final Map<String, Attribute> attributes = new HashMap<>();

    public String getName() {
        return name;
    }

    public Structure(String name) {
        this.name = name;
    }

    public List<String> getAttributeNames() {
        return attributes.values().stream()
                .sorted(Comparator.comparingInt(Attribute::getOrdinal))
                .map(Attribute::getName)
                .collect(Collectors.toList());
    }

    public Attribute putAttribute(Attribute attribute) {
        Assert.notNull(attribute);
        Attribute replaced = attributes.put(attribute.getName(), attribute);
        if (replaced != null) {
            replaced.setStructure(null);
        }

        attribute.setStructure(this);

        return replaced;
    }

    public Attribute getAttribute(String name) {
        return attributes.get(name);
    }
}
