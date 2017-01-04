package biz.jovido.seed.content.web;

import biz.jovido.seed.content.domain.Fragment;

import javax.validation.Valid;

/**
 * @author Stephan Grundner
 */
public class FragmentForm {

    @Valid
    private Fragment fragment;

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
