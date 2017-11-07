package biz.jovido.seed.content;

import biz.jovido.seed.UUIDConverter;
import biz.jovido.seed.content.Sequence;

import javax.persistence.*;
import java.util.UUID;

/**
 * @author Stephan Grundner
 */
@Entity
@DiscriminatorColumn(name = "type")
public abstract class Payload<T> {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    @Convert(converter = UUIDConverter.class)
    private UUID uuid;

    @ManyToOne(targetEntity = Sequence.class)
    private Sequence<T> sequence;

    private int ordinal = -1;

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

    public Sequence<T> getSequence() {
        return sequence;
    }

    public void setSequence(Sequence<T> sequence) {
        this.sequence = sequence;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public abstract T getValue();
    public abstract void setValue(T value);

//    @SuppressWarnings("unchecked")
//    public Payload<T> copy() {
//        Sequence<T> sequence = getSequence();
//        Attribute attribute = sequence.getAttribute();
//        Payload<T> copy = (Payload<T>) attribute.createPayload();
//        copy.setValue(resolveValue());
//
//        return copy;
//    }

    public boolean differsFrom(Payload<T> other) {
        return true;
    }
}
