package biz.jovido.seed.security;

import biz.jovido.seed.ui.Action;
import biz.jovido.seed.ui.Breadcrumb;
import biz.jovido.seed.ui.Listing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@Controller
@RequestMapping(path = "/admin/users/")
@SessionAttributes(types = {UserListing.class})
public class UserListingController {

    @Autowired
    private SecurityService securityService;

    @ModelAttribute("breadcrumbs")
    protected List<Breadcrumb> breadcrumbs() {
        List<Breadcrumb> breadcrumbs = new ArrayList<>();
        breadcrumbs.add(new Breadcrumb("seed.home", "/admin"));
        breadcrumbs.add(new Breadcrumb("seed.user.listing.title"));
        return breadcrumbs;
    }

    @ModelAttribute
    protected UserListing listing() {
        UserListing listing = new UserListing();
//        listing.addColumn("id", "seed.user.id");
        listing.addColumn("username", "seed.user.username");
        Action createAction = new Action();
        createAction.setUrl("/admin/user/create");
        listing.getActionGroup().setDefaultAction(createAction);
        listing.setEntryFactory(new Listing.EntryFactory() {
            @Override
            public Listing.Entry createRow(Listing listing, Object source) {
                Listing.Entry entry = new Listing.Entry(listing);
                entry.setSource(source);
                Action editAction = new Action();
                editAction.setUrl("/admin/user/edit?id=" + ((User) source).getId());
                entry.setEditAction(editAction);
                return entry;
            }
        });
        return listing;
    }

    @RequestMapping
    protected String index(@ModelAttribute UserListing listing,
                           BindingResult bindingResult) {

        listing.clear();

        List<User> users = securityService.getAllUsers();
        listing.addEntries(users);

        return "admin/user/listing-page";
    }
}
