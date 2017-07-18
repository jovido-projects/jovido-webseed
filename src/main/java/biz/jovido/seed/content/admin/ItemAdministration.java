package biz.jovido.seed.content.admin;

import org.springframework.validation.Errors;

/**
 * @author Stephan Grundner
 */
public class ItemAdministration {

    private ItemListing listing;

    private ItemEditor editor;

    private Errors errors;

    public ItemListing getListing() {
        return listing;
    }

    public void setListing(ItemListing listing) {
        this.listing = listing;
    }

    public ItemEditor getEditor() {
        return editor;
    }

    public void setEditor(ItemEditor editor) {
        this.editor = editor;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }
}
