package biz.jovido.seed.content;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * @author Stephan Grundner
 */
@Entity
public class FragmentPayload extends Payload<Fragment> {

    @OneToOne
    private Fragment fragment;

    @Override
    public Fragment getValue() {
        return fragment;
    }

    @Override
    public void setValue(Fragment value) {
        fragment = value;
        fragment.setDependingPayload(this);
    }
}
