package biz.jovido.seed.web;

import java.util.List;

/**
 * @author Stephan Grundner
 */
public interface Field<E extends Editor, V extends Value> {

    E getEditor();
    String getName();

    List<V> getValues();
//    V getValue(int index);
//    void setValue(int index, V value);
}
