package biz.jovido.seed.ui;

/**
 * @author Stephan Grundner
 */
public class Editor {

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
