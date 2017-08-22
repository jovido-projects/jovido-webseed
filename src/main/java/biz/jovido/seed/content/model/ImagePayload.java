package biz.jovido.seed.content.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * @author Stephan Grundner
 */
@Entity
public class ImagePayload extends Payload<Image> {

    @OneToOne(cascade = CascadeType.ALL)
    private Image image;

    @Override
    public Image getValue() {
        return image;
    }

    @Override
    public void setValue(Image value) {
        image = value;
    }
}
