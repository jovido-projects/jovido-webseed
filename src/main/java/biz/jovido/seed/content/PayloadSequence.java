package biz.jovido.seed.content;

import biz.jovido.seed.AbstractUnique;
import biz.jovido.seed.Unique;
import biz.jovido.seed.util.ListDecorator;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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
    @OrderBy("ordinal")
    private final List<Payload> payloads = new ArrayList<>();

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
                .sorted(Comparator.comparingInt(Payload::getOrdinal))
                .collect(Collectors.toList());

        return Collections.unmodifiableList(list);
    }

    public boolean addPayload(Payload payload) {
        if (payloads.add(payload)) {
            payload.setSequence(this);
            payload.setOrdinal(payloads.size());
            FragmentChange change = new FragmentChange(fragment,
                    Collections.singletonList(payload), new ArrayList<>());
            fragment.notifyFragmentChanged(change);
            return true;
        }

        return false;
    }

    public boolean removePayload(int ordinal) {
        if (payloads.removeIf(it -> it.getOrdinal() == ordinal)) {

            return true;
        }

        return false;
    }

    public int size() {
        return payloads.size();
    }

//
//    @Override
//    protected List<Payload> decorated() {
//        return payloads;
//    }
//
//    @Override
//    public Payload set(int index, Payload payload) {
//        Assert.notNull(payload, "[payload] must not be null");
//        return payloads.set(index, payload);
//    }
//
//    @Override
//    public void add(int index, Payload payload) {
//        payloads.add(index, payload);
//
//        payload.setOrdinal(index);
//        payload.setSequence(this);
//
//        FragmentChange change = new FragmentChange(fragment,
//                Collections.singletonList(payload), new ArrayList<>());
//        fragment.notifyFragmentChanged(change);
//    }
//
//    @Override
//    public Payload remove(int index) {
//        return payloads.remove(index);
//    }
}
