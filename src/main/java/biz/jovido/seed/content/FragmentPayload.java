package biz.jovido.seed.content;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author Stephan Grundner
 */
@Entity
@DiscriminatorValue("fragment")
public class FragmentPayload extends Payload {

    @ManyToOne
    private Fragment fragment;

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
