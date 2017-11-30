package biz.jovido.seed.ui;

import biz.jovido.seed.admin.Administration;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * @author Stephan Grundner
 */
@ControllerAdvice
public class NavigationSupport {

    @ModelAttribute
    protected Navigation navigation(@ModelAttribute Administration administration) {
        Navigation navigation = administration.getNavigation();

        Actions actions = navigation.getActions();
        Action home = new Action();
        home.setText(new StaticText("Home"));
        actions.add(home);

        return navigation;
    }
}
