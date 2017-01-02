package biz.jovido.seed.content.metamodel;

import biz.jovido.seed.content.model.Fragment;

import java.util.List;

/**
 * @author Stephan Grundner
 */
public class ListAttribute<F extends Fragment, T extends List<?>> extends CollectionAttribute<F, T> {

    public ListAttribute(String name, Class<T> type, Class<?> elementType) {
        super(name, type, elementType);
    }
}
