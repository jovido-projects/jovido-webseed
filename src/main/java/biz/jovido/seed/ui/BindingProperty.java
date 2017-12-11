package biz.jovido.seed.ui;

import java.util.List;

/**
 * @author Stephan Grundner
 */
public interface BindingProperty<T> {

    String getName();

    T getValue();
    void setValue(T value);

    List<T> getValues();
    int getCapacity();
}
