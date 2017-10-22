package biz.jovido.seed.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

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

//        Page<Item> page = itemService.findAllItems(0, Integer.MAX_VALUE);
//        listing.setPage(page);

        List<Item> items = itemService.findAllItems();
        listing.setItems(items);

        return "admin/item/listing";
    }
}
