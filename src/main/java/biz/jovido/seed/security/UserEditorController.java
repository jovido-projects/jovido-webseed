package biz.jovido.seed.security;

import biz.jovido.seed.mvc.Breadcrumb;
import biz.jovido.seed.mvc.ErrorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@Controller
@RequestMapping(path = "/admin/user/")
@SessionAttributes(types = {UserEditor.class})
public class UserEditorController {

    public static class PasswordValidator implements Validator {

        @Override
        public boolean supports(Class<?> clazz) {
            return UserEditor.class.isAssignableFrom(clazz);
        }

        @Override
        public void validate(Object target, Errors errors) {
            UserEditor editor = (UserEditor) target;
            if (editor != null) {
//                String password = editor.getPassword();
//                if (StringUtils.hasLength(password) && !password.equals(editor.getPasswordRepetition())) {
//                    errors.rejectValue("passwordRepetition", "passwords.dontmatch", "Passwords don't match");
//                }
            }
        }
    }

    @Autowired
    private SecurityService securityService;

    @InitBinder
    protected void initBinder(WebDataBinder dataBinder) {
        dataBinder.addValidators(new PasswordValidator());
    }

    @ModelAttribute("breadcrumbs")
    protected List<Breadcrumb> breadcrumbs() {
        List<Breadcrumb> breadcrumbs = new ArrayList<>();
        breadcrumbs.add(new Breadcrumb("seed.home", "/admin"));
        breadcrumbs.add(new Breadcrumb("seed.user.listing.title", "/admin/users"));
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

        ErrorUtils.applyRedirectedErrors(model);

        return "admin/user/editor-page";
    }

    @GetMapping(path = "create")
    protected String create(@ModelAttribute UserEditor editor,
                            BindingResult bindingResult) {

        User user = new User();
        editor.setUser(user);

        return redirect(editor);
    }

    @GetMapping(path = "edit")
    protected String edit(@ModelAttribute UserEditor editor,
                          @RequestParam(name = "id") Long userId,
                          BindingResult bindingResult) {

        User user = securityService.getUser(userId);
        editor.setUser(user);

        return redirect(editor);
    }

    @PostMapping(path = "save")
    protected String save(@Valid @ModelAttribute UserEditor editor,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            ErrorUtils.redirectErrors(redirectAttributes, bindingResult);
            return redirect(editor);
        }

        User user = editor.getUser();
//        String newPassword = editor.getPassword().resolveValue();
//        if (StringUtils.hasLength(newPassword)) {
//            securityService.saveUser(user, newPassword);
//        }

        return "redirect:edit?id=" + user.getId();
    }
}
