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
    private Image image;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
