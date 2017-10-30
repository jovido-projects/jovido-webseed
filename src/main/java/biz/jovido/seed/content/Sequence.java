package biz.jovido.seed.content;

import biz.jovido.seed.UUIDConverter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.*;

/**
 * @author Stephan Grundner
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"item_id", "attribute_name"}))
public class Sequence<T> extends AbstractList<T> {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    @Convert(converter = UUIDConverter.class)
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "attribute_name")
    private String attributeName;

    @OneToMany(mappedBy = "sequence", targetEntity = Payload.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true)
    @OrderBy("ordinal")
    @Fetch(FetchMode.SELECT)
    private final List<Payload<T>> payloads = new ArrayList<>();

    @Transient
    private boolean collapsed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUuid() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }

        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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

    public List<Payload<T>> getPayloads() {
        return Collections.unmodifiableList(payloads);
    }

    public Payload<T> getPayload(int index) {
        if (index >= payloads.size()) {
            return null;
        }

        return payloads.get(index);
    }

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

    /**
     * Use {@link #size()} instead.
     */
    @Deprecated
    public int length() {
        return payloads.size();
    }

    @Override
    public T get(int index) {
        int size = payloads.size();
        if (index >= size) {
            return null;
        }

        return payloads.get(index).getValue();
    }

    @Override
    public int size() {
        return length();
    }
}
