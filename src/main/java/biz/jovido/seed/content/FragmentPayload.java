package biz.jovido.seed.content;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * @author Stephan Grundner
 */
@Entity
public class FragmentPayload extends Payload {

    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Fragment fragment;

    @Override
    public Fragment getValue() {
        return fragment;
    }

    @Override
    public void setValue(Object value) {
        fragment = (Fragment) value;

        if (fragment != null) {
            fragment.setReferringPayload(this);
        }
    }
}
