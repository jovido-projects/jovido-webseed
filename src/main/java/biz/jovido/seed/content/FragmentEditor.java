package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class FragmentEditor {

    private FragmentForm form;

    public FragmentForm getForm() {
        return form;
    }

    public void setForm(FragmentForm form) {
        this.form = form;
    }

    public Fragment getFragment() {
        return getForm().getFragment();
    }

    public void setFragment(Fragment fragment) {
        getForm().setFragment(fragment);
    }
}
