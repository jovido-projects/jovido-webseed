package biz.jovido.seed.ui;

/**
 * @author Stephan Grundner
 */
public class Listing {

    private Breadcrumbs breadcrumbs;
    private Actions actions;

    public Breadcrumbs getBreadcrumbs() {
        if (breadcrumbs == null) {
            breadcrumbs = new Breadcrumbs();
        }

        return breadcrumbs;
    }

    public void setBreadcrumbs(Breadcrumbs breadcrumbs) {
        this.breadcrumbs = breadcrumbs;
    }

    public Actions getActions() {
        if (actions == null) {
            actions = new Actions();
        }

        return actions;
    }

    public void setActions(Actions actions) {
        this.actions = actions;
    }
}
