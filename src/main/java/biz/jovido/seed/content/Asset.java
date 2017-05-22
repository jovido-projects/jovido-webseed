package biz.jovido.seed.content;

import biz.jovido.seed.UUIDConverter;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

/**
 * @author Stephan Grundner
 */
@Entity
public class Asset {

    @Id
    @GeneratedValue
    private Long id;

    @Convert(converter = UUIDConverter.class)
    private UUID uuid = UUID.randomUUID();

    private String fileName;

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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
