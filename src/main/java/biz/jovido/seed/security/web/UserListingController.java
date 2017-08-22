package biz.jovido.seed.security.web;

import biz.jovido.seed.security.model.User;
import biz.jovido.seed.security.service.SecurityService;
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
@RequestMapping(path = "/admin/users")
@SessionAttributes(types = {UserListing.class})
public class UserListingController {

    @Autowired
    private SecurityService securityService;

    @ModelAttribute
    protected UserListing listing() {
        return new UserListing();
    }

    @RequestMapping
    protected String index(@ModelAttribute UserListing listing,
                           BindingResult bindingResult) {

        Page<User> page = securityService.findAllUsers(0, Integer.MAX_VALUE);
        listing.setPage(page);

        return "admin/user/listing";
    }
}
