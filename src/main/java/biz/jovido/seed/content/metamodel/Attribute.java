package biz.jovido.seed.content.metamodel;

import biz.jovido.seed.content.model.Fragment;

/**
 * @author Stephan Grundner
 */
public class Attribute<F extends Fragment, T> {

    private FragmentType<? extends F> fragmentType;
    private final String name;
    private final Class<T> rawType;

    public FragmentType<? extends F> getFragmentType() {
        return fragmentType;
    }

    void setFragmentType(FragmentType<? extends F> fragmentType) {
        this.fragmentType = fragmentType;
    }

    public String getName() {
        return name;
    }

    public Class<T> getRawType() {
        return rawType;
    }

    public boolean isCollection() {
        return false;
    }

    public Attribute(String name, Class<T> rawType) {
        this.name = name;
        this.rawType = rawType;
    }
}
