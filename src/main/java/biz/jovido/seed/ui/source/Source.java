package biz.jovido.seed.ui.source;

import java.util.Collection;
import java.util.Set;

/**
 * @author Stephan Grundner
 */
public interface Source {

    Set<String> getPropertyNames();
    Collection<SourceProperty> getProperties();
    SourceProperty getProperty(String name);
}
