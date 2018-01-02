package biz.jovido.seed.content;

import biz.jovido.seed.AbstractUnique;

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
            FragmentChange change = new PayloadChange.PayloadAdded(fragment, attributeName, payload.getOrdinal());
            fragment.notifyFragmentChanged(change);
            return true;
        }

        return false;
    }

    private void recalculateOrdinals() {
        List<Payload> payloads = getPayloads();
        for (int i = 0; i < payloads.size(); i++) {
            Payload payload = payloads.get(i);
            payload.setOrdinal(i);
        }
    }

    public boolean removePayload(Payload payload) {
        if (payloads.removeIf(it -> Objects.equals(payload, it))) {

            recalculateOrdinals();

            FragmentChange change = new PayloadChange.PayloadRemoved(fragment, payload);
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

        FragmentChange change = new PayloadChange.PayloadsSwapped(fragment, attributeName, i, j);
        fragment.notifyFragmentChanged(change);
    }

    public int size() {
        return payloads.size();
    }
}
