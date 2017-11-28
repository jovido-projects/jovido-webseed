package biz.jovido.seed.content;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Stephan Grundner
 */
@Entity
@Table(name = "image")
public class OriginalImage extends Asset {

    private String alt;

    @OneToMany(mappedBy = "original", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private final Set<ConvertedImage> convertedImages = new HashSet<>();

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public Set<ConvertedImage> getConvertedImages() {
        return Collections.unmodifiableSet(convertedImages);
    }

    public boolean addConvertedImage(ConvertedImage convertedImage) {
        if (convertedImages.add(convertedImage)) {
            convertedImage.setOriginal(this);
            return true;
        }

        return false;
    }

    public boolean removeConvertedImage(ConvertedImage convertedImage) {
        if (convertedImages.remove(convertedImage)) {
            convertedImage.setOriginal(null);
            return true;
        }

        return false;
    }
}
