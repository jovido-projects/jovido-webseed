package biz.jovido.seed.content.web;

import biz.jovido.seed.content.ItemService;
import biz.jovido.seed.content.model.Structure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Locale;

/**
 * @author Stephan Grundner
 */
@Controller
@RequestMapping("/admin/items/")
@SessionAttributes(types = ItemMaintenance.class)
public class ItemMaintenanceController {

    @Autowired
    private ItemService itemService;

    @ModelAttribute
    protected ItemMaintenance maintenance() {
        return new ItemMaintenance(itemService);
    }

    @RequestMapping
    protected String index(@ModelAttribute ItemMaintenance maintenance,
                           BindingResult bindingResult) {

        return "admin/item/maintenance";
    }

    @RequestMapping(path = "create")
    protected RedirectView create(@ModelAttribute ItemMaintenance maintenance,
                                  BindingResult bindingResult,
                                  @RequestParam(name = "structure") String structureName) {

        Locale locale = LocaleContextHolder.getLocale();
        maintenance.create(structureName, locale);

        return new RedirectView("");
    }

    @RequestMapping(path = "save")
    protected RedirectView save(@ModelAttribute ItemMaintenance maintenance,
                                BindingResult bindingResult) {

        return new RedirectView("");
    }

    @RequestMapping(path = "append")
    protected RedirectView append(@ModelAttribute ItemMaintenance maintenance,
                                  BindingResult bindingResult,
                                  @RequestParam(name = "attribute") String attributeName,
                                  @RequestParam(name = "structure") String structureName) {


//        maintenance.append(attributeName, structureName);

        return new RedirectView("");
    }
}
