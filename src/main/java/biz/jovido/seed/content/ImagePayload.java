package biz.jovido.seed.content;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author Stephan Grundner
 */
@Entity
@DiscriminatorValue("image")
public class ImagePayload extends AssetPayload {

    @ManyToOne(cascade = CascadeType.ALL)
    private OriginalImage image;

    public OriginalImage getImage() {
        return image;
    }

    public void setImage(OriginalImage image) {
        this.image = image;
    }

    @Override
    public Payload copy() {
        ImagePayload copy = new ImagePayload();
        copy.setImage(getImage());

        return copy;
    }

    public ImagePayload() {
        super(PayloadType.IMAGE);
    }
}
