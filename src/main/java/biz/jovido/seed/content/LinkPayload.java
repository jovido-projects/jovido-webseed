package biz.jovido.seed.content;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * @author Stephan Grundner
 */
@Entity
public class LinkPayload extends Payload<Link> {

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Link link;

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    @Override
    public Link getValue() {
        return getLink();
    }

    @Override
    public void setValue(Link value) {
        link = value;
    }
}
