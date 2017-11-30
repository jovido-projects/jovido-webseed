package biz.jovido.seed.ui.source;

import java.util.Collection;
import java.util.Set;

/**
 * @author Stephan Grundner
 */
public interface Source {

    interface Property {

        String getName();

        Object getValue();
        void setValue(Object value);

        boolean isReadOnly();
    }

    Set<String> getPropertyNames();

    Collection<Property> getProperties();
    boolean containsProperty(String name);
    Property getProperty(String name);
    Property addProperty(String name);
    boolean removeProperty(String name);
}
