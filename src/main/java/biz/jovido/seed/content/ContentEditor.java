package biz.jovido.seed.content;

import org.springframework.validation.Errors;

/**
 * @author Stephan Grundner
 */
public class ContentEditor {

    private Item item;
    private Fragment fragment;
    private Errors errors;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
        fragment = item.getCurrentFragment();
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }
}
