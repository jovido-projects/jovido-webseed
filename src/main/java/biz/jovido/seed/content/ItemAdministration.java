package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class ItemAdministration {

    private ItemListing listing;
    private ItemEditor editor;

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
}
