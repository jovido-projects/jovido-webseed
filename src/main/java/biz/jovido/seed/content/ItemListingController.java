package biz.jovido.seed.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

/**
 * @author Stephan Grundner
 */
@Controller
@RequestMapping(path = "/admin/items/")
@SessionAttributes(types = {ItemListing.class})
public class ItemListingController {

    @Autowired
    private StructureService structureService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private MessageSource messageSource;

    @InitBinder
    protected void init(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("**");
    }

//    @ModelAttribute("breadcrumbs")
//    protected List<Breadcrumb> breadcrumbs() {
//        List<Breadcrumb> breadcrumbs = new ArrayList<>();
//        breadcrumbs.add(new Breadcrumb("seed.home", "/admin"));
//        breadcrumbs.add(new Breadcrumb("seed.item.listing.title"));
//        return breadcrumbs;
//    }

    @ModelAttribute
    protected ItemListing listing() {
        ItemListing listing = new ItemListing();

        structureService.findStandaloneStructures().forEach(structure -> {

        });

        return listing;
    }

    @RequestMapping
    protected String index(@ModelAttribute ItemListing listing,
                           BindingResult bindingResult) {

        List<Item> items = itemService.findAllItems();

        return "admin/item/listing-page";
    }
}
