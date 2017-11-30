package biz.jovido.seed.ui.source;

import biz.jovido.seed.util.CollectionUtils;

import java.util.*;

/**
 * @author Stephan Grundner
 */
public class BeanListContainer implements SourcesContainer, SourcesListContainer {

    private List<BeanSource> list = new ArrayList<>();
    private Set<String> propertyNames = new LinkedHashSet<>();

    @Override
    public List<Source> getSources() {
        return Collections.unmodifiableList(list);
    }

    public Set<String> getPropertyNames() {
        if (propertyNames == null) {
            propertyNames = new LinkedHashSet<>();
        }

        return Collections.unmodifiableSet(propertyNames);
    }

    public void setPropertyNames(Set<String> propertyNames) {
        this.propertyNames = propertyNames;

        for (BeanSource source : list) {
            updateProperties(source);
        }
    }

    public void setPropertyNames(String... propertyNames) {
        Set<String> names = CollectionUtils.toHashSet(propertyNames);
        setPropertyNames(names);
    }

    private void updateProperties(BeanSource source) {
        for (String propertyName : getPropertyNames()) {
            BeanSourceProperty property = (BeanSourceProperty)
                    source.getProperty(propertyName);
            if (property == null) {
                source.addProperty(propertyName);
            }
        }
    }

    public BeanSource addBean(Object bean) {
        BeanSource source = new BeanSource(bean);
        updateProperties(source);
        list.add(source);

        return source;
    }

    public void addBeans(List<?> beans) {
        for (Object bean : beans) {
            addBean(bean);
        }
    }

    public void addBeans(Object... beans) {
        addBeans(Arrays.asList(beans));
    }
}
