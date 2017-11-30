package biz.jovido.seed.ui.source;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Stephan Grundner
 */
public class BeanSourcesContainer<T> extends AbstractSourcesContainer<BeanSource<T>, BeanSourcesContainer<T>> {

    public BeanSource<T> addBean(T bean) {
        BeanSource<T> source = new BeanSource<>(bean);
        addSource(source);
        return source;
    }

    public void addBeans(List<T> beans) {
        for (T bean : beans) {
            addBean(bean);
        }
    }

    public void addBeans(T... beans) {
        addBeans(Arrays.asList(beans));
    }

    @Override
    public BeanSource<T> addSource() {
        return addBean(null);
    }

    @Override
    protected ChangeEvent<BeanSource<T>, BeanSourcesContainer<T>> createChangeEvent(BeanSource<T> source, boolean removed) {
        return new ChangeEvent<>(this, Collections.singleton(source), removed);
    }
}
