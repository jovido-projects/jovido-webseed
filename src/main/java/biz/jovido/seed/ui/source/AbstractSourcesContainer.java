package biz.jovido.seed.ui.source;

import java.util.*;

/**
 * @author Stephan Grundner
 */
public abstract class AbstractSourcesContainer<S extends AbstractSource, C extends AbstractSourcesContainer<S, C>> implements SourcesContainer<S, C> {

    private List<S> sources = new ArrayList<>();

    private Set<ChangeListener<S, C>> changeListeners = new LinkedHashSet<>();

    @Override
    public List<S> getSources() {
        return Collections.unmodifiableList(sources);
    }

    protected <E extends ChangeEvent<S, C>> void notifyContainerChanged(E event) {
        for (ChangeListener<S, C> changeListener : changeListeners) {
            changeListener.containerChanged(event);
        }
    }

    protected abstract ChangeEvent<S, C> createChangeEvent(S source, boolean removed);

    protected void addSource(S source) {
        ChangeEvent<S, C> event = createChangeEvent(source, false);
        if (sources.add(source)) {
            notifyContainerChanged(event);
        }
    }

    @Override
    public S removeSource(int index) {
        return sources.remove(index);
    }

    @Override
    public boolean addChangeListener(ChangeListener<S, C> changeListener) {
        return changeListeners.add(changeListener);
    }

    @Override
    public boolean removeChangeListener(ChangeListener<S, C> changeListener) {
        return changeListeners.remove(changeListener);
    }
}
