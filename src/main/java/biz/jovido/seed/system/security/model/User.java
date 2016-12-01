package biz.jovido.seed.system.security.model;

import javax.persistence.*;
import java.util.UUID;

/**
 * @author Stephan Grundner
 */
@Table(name = "\"user\"")
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private UUID uuid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
