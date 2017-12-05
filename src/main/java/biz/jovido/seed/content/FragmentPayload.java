package biz.jovido.seed.content;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author Stephan Grundner
 */
@Entity
@DiscriminatorValue("fragment")
public class FragmentPayload extends Payload<Fragment> {

    @ManyToOne
    private Fragment fragment;

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public Fragment getValue() {
        return fragment;
    }

    @Override
    public void setValue(Fragment value) {
        fragment = value;
    }
}
