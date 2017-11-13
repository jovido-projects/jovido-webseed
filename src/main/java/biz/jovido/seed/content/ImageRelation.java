package biz.jovido.seed.content;

import javax.persistence.*;

/**
 * @author Stephan Grundner
 */
@Entity
@DiscriminatorValue("Image")
public class ImageRelation extends AssetRelation<Image> {

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Image image;

    @Override
    public Image getTarget() {
        return image;
    }

    @Override
    public void setTarget(Image target) {
        image = target;
    }

    @Override
    public Payload copy() {
        ImageRelation copy = new ImageRelation();
        copy.setTarget(getTarget());

        return copy;
    }
}
