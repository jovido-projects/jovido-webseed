package biz.jovido.seed.security;

import biz.jovido.seed.content.Breadcrumb;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@Controller
@RequestMapping(path = "/admin/user/")
@SessionAttributes(types = {UserEditor.class})
public class UserEditorController {

    @ModelAttribute("breadcrumbs")
    protected List<Breadcrumb> breadcrumbs() {
        List<Breadcrumb> breadcrumbs = new ArrayList<>();
        breadcrumbs.add(new Breadcrumb("Home", "/"));
        breadcrumbs.add(new Breadcrumb("Administration", "/admin/"));
        breadcrumbs.add(new Breadcrumb("Users", "/admin/users/"));
        breadcrumbs.add(new Breadcrumb("User"));
        return breadcrumbs;
    }

    private String redirect(User user) {

        return "redirect:/admin/user/";
    }

    private String redirect(UserEditor editor) {
        return redirect(editor.getUser());
    }

    @ModelAttribute
    protected UserEditor editor() {
        return new UserEditor();
    }

    @RequestMapping
    protected String index(@ModelAttribute UserEditor editor,
                           BindingResult bindingResult,
                           Model model) {

        return "admin/user/editor";
    }

    @GetMapping(path = "create")
    protected String create(@ModelAttribute UserEditor editor,
                            BindingResult bindingResult) {

        User user = new User();
        editor.setUser(user);

        return redirect(editor);
    }
}
