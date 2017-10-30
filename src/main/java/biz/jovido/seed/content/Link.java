package biz.jovido.seed.content;

import biz.jovido.seed.URIConverter;

import javax.persistence.*;
import java.net.URI;

/**
 * @author Stephan Grundner
 */
@Entity
public class Link {

    @Id
    @GeneratedValue
    private Long id;

    private String text;

    @Column(length = 255 * 16)
    @Convert(converter = URIConverter.class)
    private URI uri;
    private String target;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
