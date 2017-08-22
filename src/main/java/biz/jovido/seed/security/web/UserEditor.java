package biz.jovido.seed.security.web;

import biz.jovido.seed.security.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephan Grundner
 */
public class UserEditor {

    private User user;

    private List<String> selectedAuthorities;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getSelectedAuthorities() {
        return selectedAuthorities;
    }

    public void setSelectedAuthorities(List<String> selectedAuthorities) {
        this.selectedAuthorities = selectedAuthorities;
    }
}
