package biz.jovido.seed.content;

import javax.persistence.*;

/**
 * @author Stephan Grundner
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"original_id", "style_name"}))
public class ConvertedImage extends Asset {

    @ManyToOne
    @JoinColumn(name = "original_id")
    private OriginalImage original;

    @Column(name = "style_name")
    private String styleName;

    public OriginalImage getOriginal() {
        return original;
    }

    /*public*/ void setOriginal(OriginalImage original) {
        this.original = original;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }
}
