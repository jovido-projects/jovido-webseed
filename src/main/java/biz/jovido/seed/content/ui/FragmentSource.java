package biz.jovido.seed.content.ui;

import biz.jovido.seed.content.Fragment;
import biz.jovido.seed.content.FragmentService;
import biz.jovido.seed.ui.Source;
import biz.jovido.seed.ui.SourceProperty;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @author Stephan Grundner
 */
public class FragmentSource implements Source {

    protected final FragmentService fragmentService;
    protected final Fragment fragment;

    private final Map<String, SourceProperty> propertyByName = new HashMap<>();

    public Fragment getFragment() {
        return fragment;
    }

    @Override
    public Set<String> getPropertyNames() {
        return Collections.unmodifiableSet(propertyByName.keySet());
    }

    @Override
    public Collection<SourceProperty> getProperties() {
        return Collections.unmodifiableCollection(propertyByName.values());
    }

    @Override
    public SourceProperty getProperty(String name) {
        return propertyByName.get(name);
    }

    @Override
    public SourceProperty setProperty(String name, SourceProperty property) {
        return propertyByName.put(name, property);
    }

    @Override
    public boolean removeProperty(String name) {
        SourceProperty property = getProperty(name);
        return propertyByName.remove(name, property);
    }

    public FragmentSource(FragmentService fragmentService, Fragment fragment) {
        Assert.notNull(fragment);
        this.fragmentService = fragmentService;
        this.fragment = fragment;

        for (String attributeName : fragment.getAttributeNames()) {
            SourceProperty property = new PayloadProperty(this, attributeName);
            propertyByName.put(attributeName, property);
        }
    }
}
