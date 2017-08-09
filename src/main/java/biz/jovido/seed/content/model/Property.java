package biz.jovido.seed.content.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"item_id", "name"}))
public final class Property {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    Item item;
    String name;

    @OneToMany(mappedBy = "property")
    @OrderBy("ordinal")
    private final List<Payload> payloads = new ArrayList<>();

    @Transient
    private final PropertyChangeSupport changeSupport = new PropertyChangeSupport();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public String getName() {
        return name;
    }

    public List<Payload> getPayloads() {
        return payloads;
    }

    public Payload getPayload(int index) {
        return payloads.get(index);
    }

    public boolean addPayload(Payload payload) {
        if (payloads.add(payload)) {
            payload.property = this;
            return true;
        }

        return false;
    }
}
