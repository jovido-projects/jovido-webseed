package biz.jovido.seed.content;

import biz.jovido.seed.Unique;
import biz.jovido.seed.util.ListDecorator;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@Entity
public class PayloadList extends ListDecorator<Payload> implements Unique {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private Fragment fragment;

    @Column(name = "attribute_name")
    private String attributeName;

    @OneToMany(mappedBy = "list", targetEntity = Payload.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("ordinal")
    private final List<Payload> payloads = new ArrayList<>();

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
    protected List<Payload> decorated() {
        return payloads;
    }

    @Override
    public Payload set(int index, Payload payload) {
        Assert.notNull(payload, "[payload] must not be null");
        return payloads.set(index, payload);
    }

    @Override
    public void add(int index, Payload payload) {
        payloads.add(index, payload);

        payload.setOrdinal(index);
        payload.setList(this);

        FragmentChange change = new FragmentChange(fragment,
                Collections.singletonList(payload), new ArrayList<>());
        fragment.notifyFragmentChanged(change);
    }

    @Override
    public Payload remove(int index) {
        return payloads.remove(index);
    }
}
