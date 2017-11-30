package biz.jovido.seed.ui.source;

import java.util.Collection;
import java.util.Set;

/**
 * @author Stephan Grundner
 */
public interface SourcesContainer {

    Set<String> getPropertyNames();
    void setPropertyNames(Set<String> propertyNames);

    Collection<Source> getSources();
}
