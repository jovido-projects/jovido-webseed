package biz.jovido.seed.content.ui;

import biz.jovido.seed.content.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.view.RedirectView;

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

        return "item/maintenance";
    }

    @RequestMapping(path = "create")
    protected RedirectView create(@ModelAttribute ItemMaintenance maintenance,
                                  BindingResult bindingResult,
                                  @RequestParam(name = "structure") String structureName) {

        maintenance.create(structureName);

        return new RedirectView("");
    }
}
