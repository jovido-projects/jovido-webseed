package biz.jovido.seed.content.payload;

import biz.jovido.seed.content.Bundle;
import biz.jovido.seed.content.Payload;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author Stephan Grundner
 */
@Entity
public class BundlePayload extends Payload<Bundle> {

    @ManyToOne
    private Bundle bundle;

    @Override
    public Bundle getValue() {
        return bundle;
    }

    @Override
    public void setValue(Bundle value) {
        bundle = value;
    }

//    public Fragment getFragment() {
//        if (reference != null) {
//            return reference.getTarget();
//        }
//
//        return null;
//    }
}
