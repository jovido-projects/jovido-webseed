package biz.jovido.seed.content.ui;

import biz.jovido.seed.content.Fragment;
import biz.jovido.seed.content.FragmentService;

/**
 * @author Stephan Grundner
 */
public class FragmentEditor {

    private final FragmentForm form;

    public FragmentForm getForm() {
        return form;
    }

    public Fragment getFragment() {
        return form.getFragment();
    }

    public void setFragment(Fragment fragment) {
        form.setFragment(fragment);
    }

    public FragmentEditor(FragmentService fragmentService) {
        form = new FragmentForm(fragmentService);
    }
}
