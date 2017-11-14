package biz.jovido.seed.content;

import biz.jovido.seed.URIConverter;

import javax.persistence.*;
import java.net.URI;

/**
 * @author Stephan Grundner
 */
@Entity
@SecondaryTable(name = "link")
@DiscriminatorValue("Link")
public class Link extends Payload {

    @Column(table = "link", length = 255 * 16)
    @Convert(converter = URIConverter.class)
    private URI uri;

    @Column(table = "link")
    private String target;

    public String getText() {
        return super.getText();
    }

    public void setText(String text) {
        super.setText(text);
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

    @Override
    public Payload copy() {
        Link copy = new Link();
        copy.setUri(getUri());
        copy.setTarget(getTarget());

        return copy;
    }
}
