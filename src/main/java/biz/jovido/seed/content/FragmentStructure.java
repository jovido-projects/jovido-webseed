package biz.jovido.seed.content;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Stephan Grundner
 */
public class FragmentStructure {

    private final String name;
    private boolean publishable;

    private final Map<String, PayloadAttribute> attributeByName = new LinkedHashMap<>();

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

    public List<PayloadAttribute> getAttributes() {
        List<PayloadAttribute> attributes = attributeByName.values().stream()
                .sorted(Comparator.comparingInt(PayloadAttribute::getOrdinal))
                .collect(Collectors.toList());
        return Collections.unmodifiableList(attributes);
    }

    public PayloadAttribute getAttribute(String name) {
        return attributeByName.get(name);
    }

    public PayloadAttribute setAttribute(String name, PayloadAttribute attribute) {
        if (attribute != null) {
            attribute.setStructure(this);
            attribute.setName(name);
            attribute.setOrdinal(attributeByName.size());
        }

        PayloadAttribute replaced = attributeByName.put(name, attribute);
        if (replaced != null) {
            replaced.setStructure(null);
            replaced.setName(null);
            replaced.setOrdinal(-1);
        }

        return replaced;
    }

    public FragmentStructure(String name) {
        this.name = name;
    }
}
