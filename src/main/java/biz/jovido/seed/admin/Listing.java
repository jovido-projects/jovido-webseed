package biz.jovido.seed.admin;

/**
 * @author Stephan Grundner
 */
public class Listing {

    private Breadcrumbs breadcrumbs;

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
