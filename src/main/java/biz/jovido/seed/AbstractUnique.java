package biz.jovido.seed;

import javax.persistence.*;
import java.util.UUID;

/**
 * @author Stephan Grundner
 */
@MappedSuperclass
public class AbstractUnique implements Unique {

    @Access(AccessType.PROPERTY)
    @Id
    @GeneratedValue
    private Long id;

    @Access(AccessType.PROPERTY)
    @Column(unique = true)
    @Convert(converter = UUIDConverter.class)
    private UUID uuid;

    @Override
    public Long getId() {
        return id;
    }

    /*public*/ void setId(Long id) {
        this.id = id;
    }

    @Override
    public UUID getUuid() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }

        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
