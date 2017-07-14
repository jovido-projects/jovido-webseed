package biz.jovido.seed.content.admin;

import biz.jovido.seed.content.Fragment;
import biz.jovido.seed.content.Item;
import org.springframework.validation.Errors;

/**
 * @author Stephan Grundner
 */
public class ItemEditor {

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
