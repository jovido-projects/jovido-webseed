package biz.jovido.seed.content;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Stephan Grundner
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"item_id", "name"}))
public class Property<T> {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false, cascade = {})
    private Item item;

    private String name;

    @OneToMany(mappedBy = "property",
            targetEntity = Payload.class,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    @OrderBy("ordinal")
    private final List<Payload<T>> payloads = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public Item getItem() {
        return item;
    }

    /* public */ void setItem(Item item) {
        this.item = item;
    }

    public String getName() {
        return name;
    }

    /* public */ void setName(String name) {
        this.name = name;
    }

    public List<Payload<T>> getPayloads() {
        return Collections.unmodifiableList(payloads);
    }

    public boolean addPayload(Payload<T> payload) {
        if (payloads.add(payload)) {
            payload.setProperty(this);
            return true;
        }

        return false;
    }

    public Payload<T> getPayload(int index) {
        return payloads.get(index);
    }

    public Payload<T> setPayload(int index, Payload<T> payload) {
        Payload<T> replaced = payloads.set(index, payload);

        if (replaced != null) {
            replaced.setProperty(null);
        }

        if (payload != null) {
            payload.setProperty(this);
        }

        return replaced;
    }

    public Payload<T> removePayload(int index) {
        Payload<T> payload = payloads.remove(index);
        if (payload != null) {
            payload.setProperty(null);
        }

        return payload;
    }

    public void swapPayloads(int i, int j) {
        Payload<T> x = setPayload(i, setPayload(j, getPayload(i)));
        x.setProperty(this);
//        Payload<T> tmp = getPayload(j);
//        setPayload(j, getPayload(i));
//        setPayload(i, tmp);
    }

    public List<T> getValues() {
        return payloads.stream()
                .map(Payload::getValue)
                .collect(Collectors.toList());
    }

    public T getValue(int index) {
        Payload<T> payload = payloads.get(index);
        return payload.getValue();
    }

    public T setValue(int index, T value) {
        Payload<T> payload = payloads.get(index);
        T tmp = payload.getValue();
        payload.setValue(value);
        return tmp;
    }

    public T getValue() {
        return getValue(0);
    }

    public T setValue(T value) {
        return setValue(0, value);
    }

    @SuppressWarnings("unchecked")
    public T appendNewValue() {
        Bundle bundle = item.getBundle();
        Structure structure = bundle.getStructure();
        Attribute attribute = structure.getAttribute(name);
        Payload<T> payload = (Payload<T>) attribute.createPayload();

        if (addPayload(payload)) {
            return payload.getValue();
        }

        return null;
    }

    public Attribute getAttribute() {
        Bundle bundle = item.getBundle();
        Structure structure = bundle.getStructure();
        return structure.getAttribute(name);
    }
}
