package biz.jovido.seed.content;

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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public Image getValue() {
        return image;
    }

    @Override
    public void setValue(Image value) {
        image = value;
    }
}
