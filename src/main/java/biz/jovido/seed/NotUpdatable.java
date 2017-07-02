package biz.jovido.seed;

import javax.persistence.PreUpdate;

/**
 * @author Stephan Grundner
 */
public interface NotUpdatable {

    @PreUpdate
    default void preventUpdate() {
        throw new UnsupportedOperationException();
    }
}
