package biz.jovido.seed.content;

import biz.jovido.seed.AbstractUnique;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    @OrderBy("ordinal")
    private final List<Payload> payloads = new ArrayList<>();

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

    public boolean addPayload(Payload payload) {
        if (payloads.add(payload)) {
            payload.setGroup(this);

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

            return true;
        }

        return false;
    }

    public PayloadGroup copy() {
        PayloadGroup copy = new PayloadGroup();

        for (Payload payload : payloads) {
            copy.addPayload(payload.copy());
        }

        return copy;
    }
}
