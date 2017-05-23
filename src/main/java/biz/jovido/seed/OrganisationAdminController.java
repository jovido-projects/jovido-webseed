package biz.jovido.seed;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author Stephan Grundner
 */
@Controller
@RequestMapping("/admin/organisations")
@SessionAttributes("organisationAdminViewState")
public class OrganisationAdminController {

    @ModelAttribute("organisationAdminViewState")
    protected OrganisationAdminViewState viewState() {
        return new OrganisationAdminViewState();
    }

    @RequestMapping(path = "show")
    protected String show(@ModelAttribute("organisationAdminViewState") OrganisationAdminViewState viewState, @RequestParam(name = "form") String formId) {

        OrganisationAdminForm form = viewState.getForm(formId);
        viewState.setCurrentForm(form);

        return "admin/organisation/form";
    }

    @RequestMapping(path = "create")
    protected String create(@ModelAttribute("organisationAdminViewState") OrganisationAdminViewState viewState,
                            RedirectAttributes redirectAttributes) {

        Organisation organisation = new Organisation();
        OrganisationAdminForm form = new OrganisationAdminForm();
        viewState.putForm(form);

        redirectAttributes.addAttribute("form", form.getId());
        return "redirect:show";
    }
}
