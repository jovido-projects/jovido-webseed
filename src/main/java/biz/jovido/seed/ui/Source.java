package biz.jovido.seed.ui;

import java.util.Collection;
import java.util.Set;

/**
 * @author Stephan Grundner
 */
public interface Source {

    Set<String> getPropertyNames();
    Collection<SourceProperty> getProperties();
    SourceProperty getProperty(String name);
    SourceProperty setProperty(String name, SourceProperty property);
    boolean removeProperty(String name);
}
