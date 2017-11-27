package biz.jovido.seed.admin;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * @author Stephan Grundner
 */
@ControllerAdvice
public class NavigationSupport {

    @ModelAttribute
    protected Navigation navigation() {
        Navigation navigation = new Navigation();

        Actions actions = navigation.getActions();
        Action home = new Action();
        home.setText(new StaticText("Home"));
        actions.add(home);

        return navigation;
    }
}
