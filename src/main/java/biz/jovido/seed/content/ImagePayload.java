package biz.jovido.seed.content;

import javax.persistence.*;

/**
 * @author Stephan Grundner
 */
@Entity
@DiscriminatorValue("Image")
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
        return getImage();
    }

    @Override
    public void setValue(Image value) {
        image = value;
    }
}
