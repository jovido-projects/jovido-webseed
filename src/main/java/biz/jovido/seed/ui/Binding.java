package biz.jovido.seed.ui;

import java.util.Collection;
import java.util.Set;

/**
 * @author Stephan Grundner
 */
public interface Binding {

    Set<String> getPropertyNames();
    Collection<BindingProperty<?>> getProperties();
    BindingProperty<?> getProperty(String name);
    BindingProperty<?> setProperty(String name, BindingProperty<?> property);
    boolean removeProperty(String name);
}
