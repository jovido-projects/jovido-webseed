package biz.jovido.seed.content.model.node.fragment;

import biz.jovido.seed.content.model.node.Field;
import biz.jovido.seed.content.model.node.Fragment;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@Table(name = "node_fragment_property", uniqueConstraints = @UniqueConstraint(columnNames = {"field_id", "fragment_id"}))
@Entity
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;

    @ManyToOne
    @JoinColumn(name = "fragment_id")
    private Fragment fragment;

    @OneToMany(mappedBy = "property", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name = "ordinal")
    @OrderBy("ordinal ASC")
    private final List<Payload> payloads = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    @Transient
    public String getName() {
        return field.getName();
    }

    public List<Payload> getPayloads() {
        return Collections.unmodifiableList(payloads);
    }

    public Payload getPayload(int index) {
        if (index >= payloads.size()) {
            return null;
        }

        return payloads.get(index);
    }

    public void setPayload(int index, Payload payload) {
        Integer requiredSize = index + 1;
        if (requiredSize > size()) {
            setSize(requiredSize);
        }

        payload.setProperty(this);
        payload.setOrdinal(index);
        payloads.set(index, payload);
    }

    public Payload removePayload(int index) {
        Payload removed = payloads.remove(index);
        removed.setProperty(this);

        for (int i = index; i < payloads.size(); i++) {
            Payload payload = getPayload(i);
            payload.setOrdinal(i);
        }

        return removed;
    }

    public int size() {
        return payloads.size();
    }

    public void setSize(int size) {
        int difference = size - payloads.size();
        if (difference > 0) {
            for (int i = 0; i < difference; i++) {
                payloads.add(null);
            }

            Assert.isTrue(payloads.size() == size);
        } else if (difference < 0) {
            for (int i = difference; i < 0; i++) {
                payloads.remove(payloads.size() - 1);
            }
        }
    }
}
