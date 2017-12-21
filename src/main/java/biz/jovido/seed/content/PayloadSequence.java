package biz.jovido.seed.content;

import biz.jovido.seed.AbstractUnique;
import biz.jovido.seed.content.event.FragmentChange;
import biz.jovido.seed.content.event.PayloadAdded;
import biz.jovido.seed.content.event.PayloadRemoved;
import biz.jovido.seed.content.event.PayloadsSwapped;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

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
    private final Set<Payload> payloads = new LinkedHashSet<>();

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

    public List<Payload> getPayloads() {
        List<Payload> list = payloads.stream()
                .sorted(Comparator.comparingInt(
                        Payload::getOrdinal))
                .collect(Collectors.toList());

        return Collections.unmodifiableList(list);
    }

    public boolean addPayload(Payload payload) {
        if (payloads.add(payload)) {
            payload.setSequence(this);
            payload.setOrdinal(payloads.size() - 1);
            FragmentChange change = new PayloadAdded(fragment, attributeName, payload.getOrdinal());
            fragment.notifyFragmentChanged(change);
            return true;
        }

        return false;
    }

    public boolean removePayload(int ordinal) {
        Payload payload = getPayloads().get(ordinal);
        if (payloads.remove(payload)) {

            FragmentChange change = new PayloadRemoved(fragment, payload);
            fragment.notifyFragmentChanged(change);
            return true;
        }

        return false;
    }

    public void swapPayloads(int i, int j) {
        Payload some = getPayloads().get(i);
        Payload other = getPayloads().get(j);
        some.setOrdinal(j);
        other.setOrdinal(i);

        FragmentChange change = new PayloadsSwapped(fragment, attributeName, i, j);
        fragment.notifyFragmentChanged(change);
    }

    public int size() {
        return payloads.size();
    }
}
