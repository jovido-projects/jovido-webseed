package biz.jovido.seed.content;

import biz.jovido.seed.UUIDConverter;

import javax.persistence.*;
import java.util.UUID;

/**
 * @author Stephan Grundner
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Asset {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    @Convert(converter = UUIDConverter.class)
    private UUID uuid;

    private String fileName;

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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
