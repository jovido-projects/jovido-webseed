package biz.jovido.seed.net;

import biz.jovido.seed.AbstractUnique;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.net.URI;

/**
 * @author Stephan Grundner
 */
@Entity
public class Host extends AbstractUnique {

    @Column(unique = true)
    private String name;

    private URI uri;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }
}
