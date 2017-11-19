package biz.jovido.seed.net;

import biz.jovido.seed.AbstractUnique;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author Stephan Grundner
 */
@Entity
//@Table(name = "")
public class Host extends AbstractUnique {

    @Column(unique = true)
    private String name;

    private String path;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
