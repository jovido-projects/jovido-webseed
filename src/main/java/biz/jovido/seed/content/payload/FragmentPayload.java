package biz.jovido.seed.content.payload;

import biz.jovido.seed.content.Fragment;
import biz.jovido.seed.content.Payload;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author Stephan Grundner
 */
@Entity
public class FragmentPayload extends Payload<Fragment> {

    @ManyToOne
    private Fragment fragment;

    @Override
    public Fragment getValue() {
        return fragment;
    }

    @Override
    public void setValue(Fragment value) {
        fragment = value;
    }
}
