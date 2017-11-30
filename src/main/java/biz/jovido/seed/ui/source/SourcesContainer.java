package biz.jovido.seed.ui.source;

import java.util.Collection;
import java.util.EventListener;
import java.util.EventObject;
import java.util.List;

/**
 * @author Stephan Grundner
 */
public interface SourcesContainer<S extends Source, C extends SourcesContainer<S, ? extends C>> {

    class ChangeEvent<S extends Source, C extends SourcesContainer<S, ? extends C>> extends EventObject {

        private final Collection<S> sources;
        private final boolean removed;

        public Collection<S> getSources() {
            return sources;
        }

        public boolean removed() {
            return removed;
        }

        public boolean added() {
            return !removed;
        }

        public ChangeEvent(C container, Collection<S> sources, boolean removed) {
            super(container);
            this.sources = sources;
            this.removed = removed;
        }
    }

    interface ChangeListener<S extends Source, C extends SourcesContainer<S, ? extends C>> extends EventListener {

        void containerChanged(ChangeEvent<S, C> event);
    }

    List<S> getSources();

    S addSource();
    S removeSource(int index);

    default S getSource(int index) {
        return getSources().get(index);
    }

    boolean addChangeListener(ChangeListener<S, C> changeListener);
    boolean removeChangeListener(ChangeListener<S, C> changeListener);
}
