package biz.jovido.seed.content;

import biz.jovido.seed.AbstractUnique;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Stephan Grundner
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"item_id", "attribute_name"}))
public class PayloadGroup extends AbstractUnique {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "attribute_name")
    private String attributeName;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
//    @OrderBy("ordinal")
    private final Set<Payload> payloads = new HashSet<>();

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
        List<Payload> list = payloads.stream()
                .sorted(Comparator.comparingInt(Payload::getOrdinal))
                .collect(Collectors.toList());

        return Collections.unmodifiableList(list);
    }

    public boolean addPayload(Payload payload) {
        if (payloads.add(payload)) {

            payload.setGroup(this);
            payload.setOrdinal(payloads.size() - 1);

            if (item != null) {
                for (ItemChangeListener changeListener : item.getChangeListeners()) {
                    changeListener.payloadAdded(item, payload);
                }
            }

            return true;
        }

        return false;
    }

    public boolean removePayload(Payload payload) {
        if (payloads.remove(payload)) {

            for (ItemChangeListener changeListener : item.getChangeListeners()) {
                changeListener.payloadRemoved(item, payload);
            }

            payload.setGroup(null);
//            payload.setOrdinal(-1);

            int i = 0;
            Iterator<Payload> payloadItr = payloads.iterator();
            while (payloadItr.hasNext()) {
                Payload next = payloadItr.next();
                next.setOrdinal(i++);
            }

            return true;
        }

        return false;
    }

    public PayloadGroup copy() {
        PayloadGroup copy = new PayloadGroup();

        for (Payload payload : getPayloads()) {
            copy.addPayload(payload.copy());
        }

        return copy;
    }
}
