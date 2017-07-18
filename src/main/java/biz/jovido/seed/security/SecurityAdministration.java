package biz.jovido.seed.security;

/**
 * @author Stephan Grundner
 */
public class SecurityAdministration {

    private UserListing listing;
    private UserEditor editor;

    public UserListing getListing() {
        return listing;
    }

    public void setListing(UserListing listing) {
        this.listing = listing;
    }

    public UserEditor getEditor() {
        return editor;
    }

    public void setEditor(UserEditor editor) {
        this.editor = editor;
    }
}
