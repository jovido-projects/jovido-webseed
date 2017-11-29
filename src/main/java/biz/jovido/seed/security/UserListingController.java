package biz.jovido.seed.security;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(path = "/admin/users/")
@SessionAttributes(types = {UserListing.class})
public class UserListingController {

    @Autowired
    private SecurityService securityService;

//    @ModelAttribute("breadcrumbs")
//    protected List<Breadcrumb> breadcrumbs() {
//        List<Breadcrumb> breadcrumbs = new ArrayList<>();
//        breadcrumbs.add(new Breadcrumb("seed.home", "/admin"));
//        breadcrumbs.add(new Breadcrumb("seed.user.listing.title"));
//        return breadcrumbs;
//    }

    @ModelAttribute
    protected UserListing listing() {
        UserListing listing = new UserListing();

        return listing;
    }

    @RequestMapping
    protected String index(@ModelAttribute UserListing listing,
                           BindingResult bindingResult) {

        List<User> users = securityService.getAllUsers();

        return "admin/user/listing-page";
    }
}
