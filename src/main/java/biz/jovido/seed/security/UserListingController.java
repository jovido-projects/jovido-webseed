package biz.jovido.seed.security;

import biz.jovido.seed.content.Breadcrumb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
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
        breadcrumbs.add(new Breadcrumb("Home", "/"));
        breadcrumbs.add(new Breadcrumb("Administration", "/admin/"));
        breadcrumbs.add(new Breadcrumb("Users"));
        return breadcrumbs;
    }

    @ModelAttribute
    protected UserListing listing() {
        return new UserListing();
    }

    @RequestMapping
    protected String index(@ModelAttribute UserListing listing,
                           BindingResult bindingResult) {

        List<User> users = listing.getUsers();
        if (CollectionUtils.isEmpty(users)) {
            users = securityService.getAllUsers();
            listing.setUsers(users);
        }

        return "admin/user/listing";
    }
}
