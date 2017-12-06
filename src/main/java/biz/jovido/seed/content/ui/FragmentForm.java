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

    private <T> Field<T> createField(Payload<T> payload) {
        PayloadList<? extends Payload<T>> list = payload.getList();
        PayloadAttribute<T> attribute = fragmentService.getAttribute(list);
        FragmentSource.PayloadProperty<T> property = new FragmentSource.PayloadProperty<>(list, attribute.getCapacity());
        Field<T> field = attribute.createField(property);

        return field;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;



    }

    public FragmentForm(FragmentService fragmentService) {
        this.fragmentService = fragmentService;
    }
}
