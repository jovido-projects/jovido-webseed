package biz.jovido.seed.content;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"fragment_id", "name"}))
public final class Field {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private Fragment fragment;
    private String name;

    @Fetch(FetchMode.SELECT)
    @OneToMany(mappedBy = "field",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @OrderBy("ordinal")
    private final List<Payload> payloads = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Fragment getFragment() {
        return fragment;
    }

    /* public */ void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public String getName() {
        return name;
    }

    /* public */ void setName(String name) {
        this.name = name;
    }

    public List<Payload> getPayloads() {
//        TODO ändern:
//        return Collections.unmodifiableList(payloads.stream().collect(Collectors.toList()));
        return Collections.unmodifiableList(payloads);
    }

    public boolean appendPayload(Payload payload) {
        if (payloads.add(payload)) {
            payload.setField(this);
            payload.setOrdinal(payloads.size() - 1);
            return true;
        }

        return false;
    }

    public Payload removePayload(int index) {
        Payload removed = payloads.remove(index);
        if (removed != null) {
            removed.setField(null);
        }

        int size = payloads.size();
        for (int i = index; i < size; i++) {
            Payload payload = payloads.get(i);
            payload.setOrdinal(i);
        }

        return removed;
    }

    public void rearrangePayload(int i, int j) {
        payloads.get(i).setOrdinal(j);
        payloads.get(j).setOrdinal(i);
        Collections.swap(payloads, i, j);
    }

    public int size() {
        return payloads.size();
    }

    public Attribute getAttribute() {
        if (fragment != null) {
            Structure structure = fragment.getStructure();
            if (structure != null) {
                return structure.getAttribute(name);
            }
        }

        return null;
    }

    public Field(String name) {
        this.name = name;
    }

    public Field() {}
}
