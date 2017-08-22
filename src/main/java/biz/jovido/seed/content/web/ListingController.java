package biz.jovido.seed.content.web;

import biz.jovido.seed.content.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * @author Stephan Grundner
 */
@Controller
@RequestMapping("/admin/items")
@SessionAttributes(types = {ItemListing.class})
public class ListingController {

    @Autowired
    private ItemService itemService;

    @ModelAttribute
    protected ItemListing listing() {
        return new ItemListing();
    }

    @RequestMapping
    protected String index(@ModelAttribute ItemListing listing,
                           BindingResult bindingResult) {

        return "admin/item/editor";
    }
}
