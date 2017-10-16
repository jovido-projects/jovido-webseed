package biz.jovido.seed.content;

import javax.persistence.Entity;

/**
 * @author Stephan Grundner
 */
@Entity
public class Image extends Asset {

    private String alt;

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }
}
