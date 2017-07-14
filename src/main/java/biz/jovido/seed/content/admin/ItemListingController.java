package biz.jovido.seed.content.admin;

import biz.jovido.seed.content.Item;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * @author Stephan Grundner
 */
@Controller
@RequestMapping("/admin/items/")
@SessionAttributes(types = {ItemListing.class, ItemAdministration.class})
public class ItemListingController {

    @ModelAttribute
    protected ItemListing listing(@ModelAttribute ItemAdministration administration) {
        return administration.getListing();
    }

    @InitBinder
    protected void init(WebDataBinder dataBinder) {
//        dataBinder.setAllowedFields("");
    }

    @RequestMapping
    protected String index(@ModelAttribute ItemListing listing,
                           BindingResult bindingResult,
                           Model model) {

        listing.getItems().clear();;
        listing.getItems().add(new Item());
        listing.getItems().add(new Item());
        listing.getItems().add(new Item());

        return "admin/item/listing";
    }
}
