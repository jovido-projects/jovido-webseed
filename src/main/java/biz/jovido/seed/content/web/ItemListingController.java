package biz.jovido.seed.content.web;

import biz.jovido.seed.content.ItemService;
import biz.jovido.seed.content.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * @author Stephan Grundner
 */
@Controller
@RequestMapping(path = "/admin/items")
@SessionAttributes(types = {ItemListing.class})
public class ItemListingController {

    @Autowired
    private ItemService itemService;

    @ModelAttribute
    protected ItemListing listing() {
        return new ItemListing();
    }

    @RequestMapping
    protected String index(@ModelAttribute ItemListing listing,
                           BindingResult bindingResult) {

        Page<Item> page = itemService.findAllItems(0, Integer.MAX_VALUE);
        listing.setPage(page);

        return "admin/item/listing";
    }
}
