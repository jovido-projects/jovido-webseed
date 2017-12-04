package biz.jovido.seed.content;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author Stephan Grundner
 */
@Entity
@DiscriminatorValue("image")
public class ImagePayload extends AssetPayload<Image> {

    @ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;

    @Override
    public Image getAsset() {
        return image;
    }

    @Override
    public void setAsset(Image asset) {
        image = asset;
    }
}
