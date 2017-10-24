package biz.jovido.seed.content;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"item_id", "attribute_name"}))
public class Sequence<T> extends AbstractList<T> {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "attribute_name")
    private String attributeName;

    @OneToMany(mappedBy = "sequence", targetEntity = Payload.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderBy("ordinal")
    @Fetch(FetchMode.SELECT)
    private final List<Payload<T>> payloads = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getAttributeName() {
        return attributeName;
    }

    /*public*/ void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    @Transient
    public Attribute getAttribute() {
        Item item = getItem();
        if (item != null) {
            Structure structure = item.getStructure();
            if (structure != null) {
                String attributeName = getAttributeName();
                if (StringUtils.hasLength(attributeName)) {
                    return structure.getAttribute(attributeName);
                }
            }
        }

        return null;
    }

    public List<Payload<T>> getPayloads() {
        return Collections.unmodifiableList(payloads);
    }

    public Payload<T> getPayload(int index) {
        if (index >= payloads.size()) {
            return null;
        }

        return payloads.get(index);
    }
//
//    @Deprecated
//    public Payload addPayload() {
//        Structure structure = item.getStructure();
//        Attribute attribute = structure.getAttribute(attributeName);
//        Payload payload = attribute.createPayload();
//        if (payloads.add(payload)) {
//            payload.setSequence(this);
//            payload.setOrdinal(payloads.size() - 1);
//        }
//
//        return payload;
//    }

    public Payload<T> addPayload(Payload<T> payload) {
        if (payloads.add(payload)) {
            payload.setSequence(this);
            payload.setOrdinal(payloads.size() - 1);
        }

        return payload;
    }

    public void removePayload(int index) {
        Payload<T> removed = payloads.remove(index);
        if (removed != null) {
            removed.setSequence(null);
            removed.setOrdinal(-1);

            for (int i = index; i < payloads.size(); i++) {
                Payload payload = payloads.get(i);
                payload.setOrdinal(i);
            }
        }
    }

    public void movePayload(int fromIndex, int toIndex) {
        Collections.swap(payloads, fromIndex, toIndex);
        for (int i = 0; i < payloads.size(); i++) {
            Payload payload = payloads.get(i);
            payload.setOrdinal(i);
        }
    }

    public int length() {
        return payloads.size();
    }

    @Override
    public T get(int index) {
        return payloads.get(index).getValue();
    }

    @Override
    public int size() {
        return length();
    }

    public Sequence<T> copy() {
        Sequence<T> copy = new Sequence<>();
        for (int i = 0; i < length(); i++) {
            Payload<T> payload = getPayload(i);
            if (payload != null) {
                copy.addPayload(payload.copy());
            }
        }
        return copy;
    }
}
