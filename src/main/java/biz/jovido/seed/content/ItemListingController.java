package biz.jovido.seed.content;

import biz.jovido.seed.security.User;
import biz.jovido.seed.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.ArrayList;
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

    @InitBinder
    protected void init(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("**");
    }

    @ModelAttribute("breadcrumbs")
    protected List<Breadcrumb> breadcrumbs() {
        List<Breadcrumb> breadcrumbs = new ArrayList<>();
        breadcrumbs.add(new Breadcrumb("seed.home", "/admin"));
        breadcrumbs.add(new Breadcrumb("seed.item.listing.title"));
        return breadcrumbs;
    }

    @ModelAttribute
    protected ItemListing listing() {
        ItemListing listing = new ItemListing();
//        listing.addColumn("id", "seed.item.id");

        HasColumns.Column labelColumn = listing.addColumn("label", "seed.item.label");
        labelColumn.setValueResolver((HasColumns.Column column, Object source) -> {
            Item item = (Item) source;
            return "juhu";
        });
        labelColumn.setValueTemplate("admin/item/listing/label-value");

        listing.addColumn("structure", "seed.item.structure").setValueResolver(new HasColumns.ValueResolver() {
            @Override
            public Object resolveValue(HasColumns.Column column, Object source) {
                Item item = (Item) source;
                Structure structure = structureService.getStructure(item.getStructureName());
                return structure.getName();
            }
        });
        listing.addColumn("locale", "seed.item.locale");
        listing.addColumn("createdBy", "seed.item.createdBy").setValueResolver(new HasColumns.ValueResolver() {
            @Override
            public Object resolveValue(HasColumns.Column column, Object source) {
                Item item = (Item) source;
                User user = item.getCreatedBy();
                return user != null ? user.getUsername() : null;
            }
        });
        listing.addColumn("lastModifiedAt", "seed.item.lastModifiedAt");
        ActionGroup actionGroup = listing.getActionGroup();
        actionGroup.setMessageCode("seed.item.listing.create");
        structureService.findPublishableStructures().forEach(structure -> {
            Action action = new Action();
            action.setDefaultMessage(structure.getName());
            action.setUrl("/admin/item/create?structure=" + structure.getName());
            actionGroup.addAction(action);
        });

        listing.setEntryFactory(new Listing.EntryFactory() {
            @Override
            public Listing.Entry createRow(Listing listing, Object source) {
                Listing.Entry entry = new Listing.Entry(listing);
                entry.setSource(source);
                Action editAction = new Action();
                editAction.setUrl("/admin/item/edit?id=" + ((Item) source).getId());
                entry.setEditAction(editAction);
                return entry;
            }
        });
        return listing;
    }

    @RequestMapping
    protected String index(@ModelAttribute ItemListing listing,
                           BindingResult bindingResult) {

//        Page<Item> page = itemService.findAllItems(0, Integer.MAX_VALUE);
//        listing.setPage(page);

        listing.clear();

        List<Item> items = itemService.findAllItems();
//        listing.setItems(items);
        listing.addEntries(items);

        return "admin/item/listing-page";
    }
}
