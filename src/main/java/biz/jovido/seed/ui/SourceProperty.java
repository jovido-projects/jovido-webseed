package biz.jovido.seed.ui;

import java.util.List;

/**
 * @author Stephan Grundner
 */
public interface SourceProperty {

    String getName();
    List<Object> getValues();
    Object getValue();
    void setValue(Object value);
    int getCapacity();
}
