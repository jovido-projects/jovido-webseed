package biz.jovido.seed.content.metamodel;

import biz.jovido.seed.content.model.Fragment;

/**
 * @author Stephan Grundner
 */
public abstract class CollectionAttribute<F extends Fragment, T> extends Attribute<F, T> {

    private final Class<?> elementType;

    @Override
    public final boolean isCollection() {
        return true;
    }

    public Class<?> getElementType() {
        return elementType;
    }

    public CollectionAttribute(String name, Class<T> type, Class<?> elementType) {
        super(name, type);
        this.elementType = elementType;
    }
}
