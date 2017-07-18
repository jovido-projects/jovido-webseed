package biz.jovido.seed.content.admin;

import biz.jovido.seed.content.Item;
import biz.jovido.seed.content.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * @author Stephan Grundner
 */
@ControllerAdvice(assignableTypes = {ItemListingController.class, ItemEditorController.class})
@SessionAttributes(types = ItemAdministration.class)
public class ItemAdministrationAdvice {

    @Autowired
    private ItemService itemService;

    @ModelAttribute
    protected ItemAdministration administration(
            @RequestParam(name = "id", required = false) Long itemId,
            @RequestParam(name = "new", required = false) String structureName) {
        ItemAdministration administration = new ItemAdministration();
        ItemListing listing = new ItemListing();
        administration.setListing(listing);
        ItemEditor editor = new ItemEditor();

        if (itemId != null) {
            Item item = itemService.findItemById(itemId);
            editor.setItem(item);
        } else if (StringUtils.hasText(structureName)) {
            Item item = itemService.createItem(structureName,
                    LocaleContextHolder.getLocale());
            editor.setItem(item);
        }

        administration.setEditor(editor);
        return administration;
    }
}
