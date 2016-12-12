package biz.jovido.seed.content.model;

import java.util.Set;

/**
 * @author Stephan Grundner
 */
public interface Bundle<T> {

    T getCurrent();

    Set<T> getRevisions();
    boolean addRevision(T revision);
}
