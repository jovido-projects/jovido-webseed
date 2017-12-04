package biz.jovido.seed.content;

import biz.jovido.seed.AbstractUnique;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Stephan Grundner
 */
@Entity
public class PayloadSequence extends AbstractUnique {

    @ManyToOne(optional = false)
    private Fragment fragment;

    @Column(name = "attribute_name")
    private String attributeName;

    @OneToMany(mappedBy = "sequence", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<Payload> payloads = new HashSet<>();

    public Fragment getFragment() {
        return fragment;
    }

    protected void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public String getAttributeName() {
        return attributeName;
    }

    protected void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public Set<Payload> getPayloads() {
        return Collections.unmodifiableSet(payloads);
    }

    public boolean addPayload(Payload payload) {
        if (payload != null) {
            if (payloads.add(payload)) {
                payload.setOrdinal(getPayloads().size() - 1);
                payload.setSequence(this);

                FragmentChange change = new FragmentChange(fragment,
                        Collections.singletonList(payload), Collections.EMPTY_LIST);
                fragment.notifyFragmentChanged(change);

                return true;
            }
        }

        return false;
    }
}
