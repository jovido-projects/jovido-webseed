package biz.jovido.seed.net;

import biz.jovido.seed.AbstractUnique;
import biz.jovido.seed.security.User;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author Stephan Grundner
 */
@Entity
public class Visitor extends AbstractUnique {

    @ManyToOne
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
