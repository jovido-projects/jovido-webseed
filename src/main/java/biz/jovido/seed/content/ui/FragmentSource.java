package biz.jovido.seed.content.ui;

import biz.jovido.seed.content.Fragment;
import biz.jovido.seed.content.FragmentService;
import biz.jovido.seed.content.PayloadList;
import biz.jovido.seed.content.PayloadValueListAdapter;
import biz.jovido.seed.ui.Source;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
public class FragmentSource implements Source {

    public static class PayloadProperty<T> implements Source.Property<T> {

        private PayloadValueListAdapter<T> values;
        private final int capacity;

        @Override
        public List<T> getValues() {
            return values;
        }

        @Override
        public T getValue() {
            return values.get(0);
        }

        @Override
        public void setValue(T value) {
            values.set(0, value);
        }

        @Override
        public int getCapacity() {
            return capacity;
        }

        public PayloadProperty(PayloadList<T> list, int capacity) {
            values = new PayloadValueListAdapter<>(list);
            this.capacity = capacity;
        }
    }

    private final FragmentService fragmentService;
    private final Fragment fragment;

    private final Map<String, Property<?>> propertyByName = new HashMap<>();

    @Override
    public Map<String, Property<?>> getProperties() {
        return Collections.unmodifiableMap(propertyByName);
    }

    public FragmentSource(FragmentService fragmentService, Fragment fragment) {
        this.fragmentService = fragmentService;
        this.fragment = fragment;
    }
}
