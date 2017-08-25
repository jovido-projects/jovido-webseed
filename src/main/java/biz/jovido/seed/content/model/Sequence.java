package biz.jovido.seed.content.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"item_id", "attribute_name"}))
public class Sequence {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private Item item;

    @Column(name = "attribute_name")
    private String attributeName;

    @OneToMany(mappedBy = "sequence", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @OrderBy("ordinal")
    private final List<Payload> payloads = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    /*default*/ void setItem(Item item) {
        this.item = item;
    }

    public String getAttributeName() {
        return attributeName;
    }

    /*default*/ void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public List<Payload> getPayloads() {
        return Collections.unmodifiableList(payloads);
    }

    public void addPayload(Payload payload) {
        if (payloads.add(payload)) {
            payload.setSequence(this);
            payload.setOrdinal(payloads.size() - 1);
        }
    }

    public void removePayload(int index) {
        Payload removed = payloads.remove(index);
        if (removed != null) {
            removed.setSequence(null);

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
}
