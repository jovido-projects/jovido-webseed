package biz.jovido.seed.content;

import biz.jovido.seed.Unique;
import biz.jovido.seed.util.ListDecorator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@Entity
public class PayloadList<P extends Payload<?>> extends ListDecorator<P> implements Unique {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private Fragment fragment;

    @Column(name = "attribute_name")
    private String attributeName;

    @OneToMany(mappedBy = "list", targetEntity = Payload.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("ordinal")
    private final List<P> payloads = new ArrayList<>();

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    @Override
    protected List<P> decorated() {
        return null;
    }

    @Override
    public P set(int index, P element) {
        return null;
    }

    @Override
    public void add(int index, P element) {

    }

    @Override
    public P remove(int index) {
        return null;
    }


//    @Override
//    protected List<? extends Payload<T>> decorated() {
//        return payloads;
//    }
//
//    @Override
//    public P set(int index, P payload) {
//        Assert.notNull(payload, "[payload] must not be null");
//        return payloads.set(index, payload);
//    }
//
//    @Override
//    public void add(int index, P payload) {
//        payloads.add(index, payload);
//
//        payload.setOrdinal(index);
//        payload.setList(this);
//
//        FragmentChange change = new FragmentChange(fragment,
//                Collections.singletonList(payload), new ArrayList<>());
//        fragment.notifyFragmentChanged(change);
//    }
//
//    @Override
//    public P remove(int index) {
//        return payloads.remove(index);
//    }
}
