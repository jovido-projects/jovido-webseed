package biz.jovido.seed.content;

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
@ControllerAdvice(assignableTypes = {ContentListingController.class, ContentEditorController.class})
@SessionAttributes(types = ContentAdministration.class)
public class ContentAdministrationAdvice {

    @Autowired
    private ItemService itemService;

    @ModelAttribute
    protected ContentAdministration administration(
            @RequestParam(name = "id", required = false) Long itemId,
            @RequestParam(name = "new", required = false) String structureName) {
        ContentAdministration administration = new ContentAdministration();
        ContentListing listing = new ContentListing();
        administration.setListing(listing);
        ContentEditor editor = new ContentEditor();

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
