package biz.jovido.seed.security.model;

import biz.jovido.seed.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.stream.Collectors;

/**
 * @author Stephan Grundner
 */
@Controller
@RequestMapping(path = "/admin/user")
@SessionAttributes(types = {UserEditor.class})
public class UserEditorController {

    @Autowired
    private SecurityService securityService;

    @ModelAttribute
    protected UserEditor editor() {
        return new UserEditor();
    }

    @RequestMapping
    protected String index(@ModelAttribute UserEditor editor,
                           BindingResult bindingResult) {

        return "admin/user/editor";
    }

    @RequestMapping(path = "create")
    protected String create(@ModelAttribute UserEditor editor,
                            BindingResult bindingResult) {

        User user = securityService.createUser();
        editor.setUser(user);

        return "redirect:";
    }

    @RequestMapping(path = "edit")
    protected String edit(@ModelAttribute UserEditor editor,
                            @RequestParam(name = "user") Long userId,
                            BindingResult bindingResult) {

        User user = securityService.getUser(userId);
        editor.setUser(user);
        editor.setSelectedAuthorities(
                user.getRoles().values().stream()
                .map(Role::getAuthority)
                .collect(Collectors.toList()));

        return "redirect:";
    }

    @RequestMapping(path = "save")
    protected String save(@ModelAttribute UserEditor editor,
                          BindingResult bindingResult) {

        User user = editor.getUser();
        for (Role role : securityService.findAllRoles()) {
            if (editor.getSelectedAuthorities().contains(role.getAuthority())) {
                user.addRole(role);
            } else {
                user.removeRole(role);
            }
        }

        user = securityService.saveUser(user);
        editor.setUser(user);

        return "redirect:";
    }
}
