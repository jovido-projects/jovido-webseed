package biz.jovido.seed.content;

import biz.jovido.seed.MimeTypeConverter;
import org.springframework.util.MimeType;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Stephan Grundner
 */
@Entity
public class Asset {

    @Id
    @GeneratedValue
    private Long id;

    @Convert(converter = MimeTypeConverter.class)
    private MimeType mimeType;

    private String fileName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MimeType getMimeType() {
        return mimeType;
    }

    public void setMimeType(MimeType mimeType) {
        this.mimeType = mimeType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
