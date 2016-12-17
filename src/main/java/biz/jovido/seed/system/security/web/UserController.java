package biz.jovido.seed.system.security.web;

import biz.jovido.seed.system.security.model.User;
import biz.jovido.seed.system.security.service.UserService;
import biz.jovido.spring.web.ui.component.Breadcrumb;
import biz.jovido.spring.web.ui.component.Fieldset;
import biz.jovido.spring.web.ui.component.Icon;
import biz.jovido.spring.web.ui.component.Pagination;
import biz.jovido.spring.web.ui.component.data.Item;
import biz.jovido.spring.web.ui.component.data.ItemMap;
import biz.jovido.spring.web.ui.component.util.FieldsetBuilder;
import biz.jovido.spring.web.ui.component.util.ItemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Stephan Grundner
 */
@RequestMapping
@Controller
public class UserController {

    public static class UserForm {

        private Long id;

        private String username;
        private String password;
        private String passwordRepetition;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPasswordRepetition() {
            return passwordRepetition;
        }

        public void setPasswordRepetition(String passwordRepetition) {
            this.passwordRepetition = passwordRepetition;
        }
    }

    @Autowired
    private UserService userService;

    @ModelAttribute("form")
    protected UserForm form() {
        UserForm form = new UserForm();

        return form;
    }

    @ModelAttribute("breadcrumb")
    protected Breadcrumb breadcrumb() {
        Breadcrumb breadcrumb = new Breadcrumb();
        breadcrumb.addItem("", "#", false).getLabel().setIcon(new Icon("home"));

        return breadcrumb;
    }

    @RequestMapping("/admin/users")
    public String list(@ModelAttribute(value = "breadcrumb", binding = false) Breadcrumb breadcrumb,
                       @RequestParam(name = "page", defaultValue = "0") int index,
                       @RequestParam(name = "size", defaultValue = "3") int size,
                       Model model) {

        breadcrumb.addItem("Users", "#", true).getLabel().setIcon(new Icon("users"));

        Page<User> page = userService.getAllUsers(index, size);

//        Table table = new Table();
//        Table.Column column1 = table.addColumn("id");
//        Table.Column column2 = table.addColumn("username");
//        Table.Column column4 = table.addColumn("action");
//
//        column4.setAlignment(Table.Alignment.RIGHT);
//        column4.setCellGenerator( (row, columnName) -> {
//            Table.Cell cell = new Table.Cell(row, columnName);
//            Button button = new Button();
//            Icon icon = new Icon();
//            icon.setName("cog");
//            button.getLabel().setIcon(icon);
//            button.setUrl("save");
//            button.setSize(Component.Size.SMALL);
//            cell.setContent(button);
//            return cell;
//        });
//
//        List<Item> items = page.getContent().stream()
//                .map(ItemUtils::toItem)
//                .collect(Collectors.toList());
//        table.setItems(items);

        Pagination pagination = new Pagination();
        pagination.setTotalPages(page.getTotalPages());
        pagination.setMaxPages(5);
        pagination.setPageSize(size);
        pagination.setActivePage(index);
        pagination.setUrlGenerator((p, pageIndex) ->
                String.format("/admin/users?page=%d&size=%d", pageIndex, size));

        model.addAttribute("pager", pagination);
        model.addAttribute("page", page);

        return "/admin/security/user/listing";
    }

    @RequestMapping("/admin/user/edit")
    public String edit(@ModelAttribute("breadcrumb") Breadcrumb breadcrumb,
                       @ModelAttribute("form") UserForm form,
                       @RequestParam(name = "id") Long id,
                       Model model) {

        User user = userService.getUser(id);

        breadcrumb.addItem("Users", "#", false).getLabel().setIcon(new Icon("users"));
        breadcrumb.addItem("User " + id, "#", true).getLabel().setIcon(new Icon("user"));

        form.setId(user.getId());
        form.setUsername(user.getUsername());

        Item formItem = ItemUtils.toItem(form);

        Fieldset fields = new FieldsetBuilder()
                .setLabelText("User Data")
                .addCompositeField()
                    .setProperty(formItem.getProperty("id"))
                    .setReadOnly(true)
                    .setIcon(new Icon("pencil"))
                .addCompositeField()
                    .setProperty(formItem.getProperty("username"))
                    .setReadOnly(true)
                    .setIcon(new Icon("pencil"))
                .addField().setProperty(formItem.getProperty("password"))
                .addField().setProperty(formItem.getProperty("passwordRepetition"))
                .build();

        model.addAttribute("fields", fields);

        return "/admin/security/user/form";
    }

    @RequestMapping("/admin/user/save")
    public String save(@ModelAttribute("breadcrumb") Breadcrumb breadcrumb,
                       @ModelAttribute("form") UserForm form,
                       ItemMap items,
                       Model model) {

//        return "/admin/security/user/form";
        return "redirect:edit?id=" + items.getItem("userForm").getProperty("id").getValue();
    }
}
