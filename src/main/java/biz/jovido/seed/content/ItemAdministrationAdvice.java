package biz.jovido.seed.content;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * @author Stephan Grundner
 */
@ControllerAdvice
@SessionAttributes(types = ItemAdministration.class)
public class ItemAdministrationAdvice {

    @ModelAttribute
    protected ItemAdministration administration() {
        ItemAdministration administration = new ItemAdministration();
        administration.setListing(new ItemListing());
        administration.setEditor(new ItemEditor());
        return administration;
    }
}
