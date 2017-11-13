package biz.jovido.seed.security;

import biz.jovido.seed.ui.Field;

import javax.validation.Valid;

/**
 * @author Stephan Grundner
 */
public class UserEditor {

    public static class TextField extends Field<String> {

        public TextField(Object object, String bindingPath) {
            super(object, bindingPath);
            setTemplate("admin/user/text-field");
        }
    }

    @Valid
    private User user;

    private final TextField username = new TextField(this, "user.username");

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;

        username.setDisabled(user != null &&
                "admin".equals(user.getUsername()));
    }

    public TextField getUsername() {
        return username;
    }
}
