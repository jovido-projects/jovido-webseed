package biz.jovido.seed.content;

import biz.jovido.seed.AbstractUnique;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * @author Stephan Grundner
 */
@Entity
public class FragmentHistory extends AbstractUnique {

    @OneToOne(optional = false)
    private Fragment current;

    @OneToOne
    private Fragment published;

    public Fragment getCurrent() {
        return current;
    }

    public void setCurrent(Fragment current) {
        this.current = current;
    }

    public Fragment getPublished() {
        return published;
    }

    public void setPublished(Fragment published) {
        this.published = published;
    }
}
