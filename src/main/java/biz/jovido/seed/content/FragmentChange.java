package biz.jovido.seed.content;

import biz.jovido.seed.content.Fragment;

/**
 * @author Stephan Grundner
 */
public abstract class FragmentChange {

    private final Fragment fragment;

    public Fragment getFragment() {
        return fragment;
    }

    public FragmentChange(Fragment fragment) {
        this.fragment = fragment;
    }
}
