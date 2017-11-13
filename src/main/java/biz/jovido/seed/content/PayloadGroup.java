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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"item_id", "attribute_name"}))
public class PayloadGroup {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "attribute_name")
    private String attributeName;

    @OneToMany(mappedBy = "group", targetEntity = Payload.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true)
    @OrderBy("ordinal")
    @Fetch(FetchMode.SELECT)
    private final List<Payload> payloads = new ArrayList<>();

    public Long getId() {
        return id;
    }

    /*public*/ void setId(Long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    /*public*/ void setItem(Item item) {
        this.item = item;
    }

    public String getAttributeName() {
        return attributeName;
    }

    /*public*/ void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
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

    public Payload addPayload(Payload payload) {
        if (payloads.add(payload)) {
            payload.setGroup(this);
            payload.setOrdinal(payloads.size() - 1);
        }

        return payload;
    }

    public void removePayload(int index) {
        Payload removed = payloads.remove(index);
        if (removed != null) {
            removed.setGroup(null);
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

    @Deprecated
    public int length() {
        return payloads.size();
    }

//    public List<Object> getValues() {
//        return getPayloads().stream()
//                .sorted(Comparator.comparingInt(Payload::getOrdinal))
//                .map(Payload::getValue)
//                .collect(Collectors.toList());
//    }
//
//    public Object getValue() {
//        Payload payload = getPayload(0);
//        if (payload != null) {
//            return payload.getValue();
//        }
//
//        return null;
//    }

    public PayloadGroup copy() {
        PayloadGroup copy = new PayloadGroup();
        for (Payload payload : getPayloads()) {
            copy.addPayload(payload.copy());
        }

        return copy;
    }
}
