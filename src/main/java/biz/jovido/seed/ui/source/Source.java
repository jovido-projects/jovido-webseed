package biz.jovido.seed.ui.source;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author Stephan Grundner
 */
public interface Source {

    interface Property<V> {

        String getName();

        List<V> getValues();
//        boolean addValue(V t);
        V removeValue(int index);

        V getValue();
        void setValue(V v);

        boolean isReadOnly();
    }

    Set<String> getPropertyNames();

    Collection<Property> getProperties();
    boolean containsProperty(String name);
    Property getProperty(String name);
    Property addProperty(String name);
    boolean removeProperty(String name);
}
