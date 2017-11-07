package biz.jovido.seed.security;

import javax.validation.Valid;

/**
 * @author Stephan Grundner
 */
public class UserEditor {

    @Valid
    private User user;

    private String password2;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }
}
