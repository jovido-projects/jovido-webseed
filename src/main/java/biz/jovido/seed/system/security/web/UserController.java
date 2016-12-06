package biz.jovido.seed.system.security.web;

import biz.jovido.seed.component.model.Listing;
import biz.jovido.seed.system.security.model.User;
import biz.jovido.seed.system.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import java.util.stream.Stream;

/**
 * @author Stephan Grundner
 */
@RequestMapping
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/admin/users")
    public String list(@RequestParam(name = "page", defaultValue = "0") int index,
                       @RequestParam(name = "size", defaultValue = "3") int size,
                       Model model) {

        Page<User> page = userService.getAllUsers(index, size);
        Listing<User> listing = new Listing<>();
        listing.setPage(page);
        listing.addColumns("id", "username");
//        Listing.Column buttonColumn = listing.addColumn("action");

        model.addAttribute("listing", listing);

        return "/admin/security/user/listing";
    }

    @PostConstruct
    void init() {
        Stream.of("user1", "user2", "user3", "user4", "user5", "user6").forEach( username -> {
            User user = new User();
            user.setUsername(username);
            userService.saveUser(user);
        });
    }
}
