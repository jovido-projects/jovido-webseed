package biz.jovido.seed.content.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * @author Stephan Grundner
 */
@Entity
public class ImagePayload extends Payload {

    @OneToOne(cascade = CascadeType.ALL)
    private Image image;

    @Override
    public Image getValue() {
        return image;
    }

    @Override
    public void setValue(Object value) {
        image = (Image) value;
    }
}
