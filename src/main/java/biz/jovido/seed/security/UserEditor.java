package biz.jovido.seed.security;

import javax.validation.Valid;

/**
 * @author Stephan Grundner
 */
public class UserEditor {

    @Valid
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
