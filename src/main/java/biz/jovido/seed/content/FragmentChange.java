package biz.jovido.seed.content;

import java.util.Collections;
import java.util.List;

/**
 * @author Stephan Grundner
 */
public final class FragmentChange {

    private final Fragment fragment;
    private final List<Payload> payloadsAdded;
    private final List<Payload> payloadsRemoved;

    public Fragment getFragment() {
        return fragment;
    }

    public List<Payload> getPayloadsAdded() {
        return payloadsAdded;
    }

    public List<Payload> getPayloadsRemoved() {
        return payloadsRemoved;
    }

    public FragmentChange(Fragment fragment, List<Payload> payloadsAdded, List<Payload> payloadsRemoved) {
        this.fragment = fragment;
        this.payloadsAdded = Collections.unmodifiableList(payloadsAdded);
        this.payloadsRemoved = Collections.unmodifiableList(payloadsRemoved);
    }
}
