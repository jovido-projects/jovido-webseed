package biz.jovido.seed.admin;

import biz.jovido.seed.ui.Breadcrumbs;
import biz.jovido.seed.ui.Navigation;
import org.springframework.context.MessageSource;

/**
 * @author Stephan Grundner
 */
public class Administration {

    private MessageSource messageSource;
    private Navigation navigation;
    private Breadcrumbs breadcrumbs;

    public MessageSource getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public Navigation getNavigation() {
        if (navigation == null) {
            navigation = new Navigation();
        }

        return navigation;
    }

    public void setNavigation(Navigation navigation) {
        this.navigation = navigation;
    }

    public Breadcrumbs getBreadcrumbs() {
        if (breadcrumbs == null) {
            breadcrumbs = new Breadcrumbs();
        }

        return breadcrumbs;
    }

    public void setBreadcrumbs(Breadcrumbs breadcrumbs) {
        this.breadcrumbs = breadcrumbs;
    }
}
