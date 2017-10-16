package biz.jovido.seed.content;

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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public Object getValue() {
        return image;
    }
}
