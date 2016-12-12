package biz.jovido.seed.system.security.web;

import biz.jovido.seed.system.security.model.User;
import biz.jovido.seed.system.security.service.UserService;
import biz.jovido.spring.web.ui.Component;
import biz.jovido.spring.web.ui.component.Button;
import biz.jovido.spring.web.ui.component.Table;
import biz.jovido.spring.web.ui.component.data.Item;
import biz.jovido.spring.web.ui.component.util.ItemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
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

        Table table = new Table();
        Table.Column column1 = table.addColumn("id");
        Table.Column column2 = table.addColumn("username");
        Table.Column column4 = table.addColumn("action");

        column4.setAlignment(Table.Alignment.RIGHT);
        column4.setCellGenerator( (row, columnName) -> {
            Table.Cell cell = new Table.Cell(row, columnName);
            Button button = new Button();
            button.setSize(Component.Size.SMALL);
            cell.setComponent(button);
            return cell;
        });


        List<Item> items = page.getContent().stream()
                .map(ItemUtils::toItem)
                .collect(Collectors.toList());

        table.setItems(items);

        model.addAttribute("table", table);

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
