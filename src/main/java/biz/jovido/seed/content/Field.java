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
public final class Field<T> {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private Fragment fragment;
    private String name;

    @Fetch(FetchMode.SELECT)
    @OneToMany(mappedBy = "field",
            targetEntity = Payload.class,
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
//    private final Set<Payload<T>> payloads = new HashSet<>();
    private final List<Payload<T>> payloads = new ArrayList<>();

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

    public List<Payload<T>> getPayloads() {
//        TODO ändern:
//        return Collections.unmodifiableList(payloads.stream().collect(Collectors.toList()));
        return Collections.unmodifiableList(payloads);
    }

    public boolean appendPayload(Payload<T> payload) {
        if (payloads.add(payload)) {
            payload.setField(this);
            payload.setOrdinal(payloads.size() - 1);
            return true;
        }

        return false;
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
