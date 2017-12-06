package biz.jovido.seed.content.ui;

import biz.jovido.seed.content.*;
import biz.jovido.seed.ui.Field;

/**
 * @author Stephan Grundner
 */
public class FragmentForm {

    private final FragmentService fragmentService;
    private Fragment fragment;

    public FragmentService getFragmentService() {
        return fragmentService;
    }

    public Fragment getFragment() {
        return fragment;
    }

    private <T, P extends Payload<T>> Field<T> createField(P payload) {
        Field<T> field = null;

        PayloadList<T> list = payload.getList();
        PayloadAttribute<? extends Payload<T>> attribute = fragmentService.getAttribute(list);


        return field;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;



    }

    public FragmentForm(FragmentService fragmentService) {
        this.fragmentService = fragmentService;
    }
}
