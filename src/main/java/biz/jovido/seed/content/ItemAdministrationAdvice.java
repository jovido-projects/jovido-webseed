package biz.jovido.seed.content;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * @author Stephan Grundner
 */
@ControllerAdvice(assignableTypes = {ItemListingController.class, ItemEditorController.class})
@SessionAttributes(types = ItemAdministration.class)
public class ItemAdministrationAdvice {

    @ModelAttribute
    protected ItemAdministration administration() {
        ItemAdministration administration = new ItemAdministration();
        ItemListing listing = new ItemListing();
        administration.setListing(listing);
        ItemEditor editor = new ItemEditor();
        administration.setEditor(editor);
        return administration;
    }
}
