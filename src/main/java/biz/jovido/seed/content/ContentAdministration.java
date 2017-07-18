package biz.jovido.seed.content;

import org.springframework.validation.Errors;

/**
 * @author Stephan Grundner
 */
public class ContentAdministration {

    private ContentListing listing;

    private ContentEditor editor;

    private Errors errors;

    public ContentListing getListing() {
        return listing;
    }

    public void setListing(ContentListing listing) {
        this.listing = listing;
    }

    public ContentEditor getEditor() {
        return editor;
    }

    public void setEditor(ContentEditor editor) {
        this.editor = editor;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }
}
