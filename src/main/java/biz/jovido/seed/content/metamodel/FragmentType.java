package biz.jovido.seed.content.metamodel;

import biz.jovido.seed.content.model.Fragment;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
public class FragmentType<F extends Fragment> {

    private final Class<F> rawType;
    private final Map<String, Attribute<F, ?>> attributes;

    public Class<F> getRawType() {
        return rawType;
    }

    public Collection<Attribute<F, ?>> getAttributes() {
        return Collections.unmodifiableCollection(attributes.values());
    }

    public boolean hasAttribute(String name) {
        return attributes.containsKey(name);
    }

    public Attribute<F, ?> getAttribute(String name) {
        return attributes.get(name);
    }

    public Attribute<F, ?> addAttribute(Attribute<F, ?> attribute) {
        Attribute<F, ?> existing = attributes.put(attribute.getName(), attribute);

        if (existing != null) {
            existing.setFragmentType(null);
        }

        attribute.setFragmentType(this);

        return existing;
    }

    boolean removeAttribute(Attribute<F, ?> attribute) {
        if (attributes.remove(attribute.getName(), attribute)) {
            attribute.setFragmentType(null);
            return true;
        }

        return false;
    }

    public FragmentType(Class<F> rawType) {
        this.rawType = rawType;

        attributes = new HashMap<>();
    }

}
