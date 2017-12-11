package biz.jovido.seed.content.ui;

import biz.jovido.seed.content.Fragment;
import biz.jovido.seed.content.FragmentService;
import biz.jovido.seed.ui.Binding;
import biz.jovido.seed.ui.BindingProperty;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @author Stephan Grundner
 */
public class FragmentBinding implements Binding {

    protected final FragmentService fragmentService;
    protected final Fragment fragment;

    private final Map<String, BindingProperty<?>> propertyByName = new HashMap<>();

    public Fragment getFragment() {
        return fragment;
    }

    @Override
    public Set<String> getPropertyNames() {
        return Collections.unmodifiableSet(propertyByName.keySet());
    }

    @Override
    public Collection<BindingProperty<?>> getProperties() {
        return Collections.unmodifiableCollection(propertyByName.values());
    }

    @Override
    public BindingProperty<?> getProperty(String name) {
        return propertyByName.get(name);
    }

    @Override
    public BindingProperty<?> setProperty(String name, BindingProperty<?> property) {
        return propertyByName.put(name, property);
    }

    @Override
    public boolean removeProperty(String name) {
        BindingProperty<?> property = getProperty(name);
        return propertyByName.remove(name, property);
    }

    public FragmentBinding(FragmentService fragmentService, Fragment fragment) {
        Assert.notNull(fragment);
        this.fragmentService = fragmentService;
        this.fragment = fragment;

        for (String attributeName : fragment.getAttributeNames()) {
            BindingProperty<?> property = new PayloadProperty(this, attributeName);
            propertyByName.put(attributeName, property);
        }
    }
}
