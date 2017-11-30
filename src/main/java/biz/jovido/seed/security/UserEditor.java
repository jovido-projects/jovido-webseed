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
            setTemplate("ui/text-field");
            setType("text");
        }
    }

    @Valid
    private User user;

    private String passwordRepetition;

    private TextField username;
    private TextField password;
    private TextField password2;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;

        username = new TextField(this, "user.username");
        if (user.getId() != null) {
            username.setDisabled(true);
        }
        password = new TextField(this, "user.password");
        password.setType("password");
        password2 = new TextField(this, "passwordRepetition");
    }

    public String getPasswordRepetition() {
        return passwordRepetition;
    }

    public void setPasswordRepetition(String passwordRepetition) {
        this.passwordRepetition = passwordRepetition;
    }

    public TextField getUsername() {
        return username;
    }

    public TextField getPassword() {
        return password;
    }

    public TextField getPassword2() {
        return password2;
    }
}
