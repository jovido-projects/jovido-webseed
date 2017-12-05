package biz.jovido.seed.ui;

import java.util.List;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
public interface Source {

    interface Property<T> {

        List<T> getValues();

        T getValue();
        void setValue(T value);

        int getCapacity();
    }

    Map<String, Property<?>> getProperties();
}
