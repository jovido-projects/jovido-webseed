package biz.jovido.seed.content.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * @author Stephan Grundner
 */
@Entity
public class LinkPayload extends Payload {

    @OneToOne(cascade = CascadeType.ALL)
    private Link link;

    @Override
    public Link getValue() {
        return link;
    }

    @Override
    public void setValue(Object value) {
        link = (Link) value;
    }
}
