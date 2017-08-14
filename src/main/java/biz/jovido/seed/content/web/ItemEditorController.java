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
@SessionAttributes(types = ItemEditor.class)
public class ItemEditorController {

    @Autowired
    private ItemService itemService;

    @ModelAttribute
    protected ItemEditor editor() {
        return new ItemEditor(itemService);
    }

    @RequestMapping
    protected String index(@ModelAttribute ItemEditor editor,
                           BindingResult bindingResult) {

        Structure structure = itemService.getStructure(editor.item);

        return "admin/item/editor";
    }

    @RequestMapping(path = "create")
    protected RedirectView create(@ModelAttribute ItemEditor editor,
                                  BindingResult bindingResult,
                                  @RequestParam(name = "structure") String structureName) {

        Locale locale = LocaleContextHolder.getLocale();
        editor.create(structureName, locale);

        return new RedirectView("");
    }

    @RequestMapping(path = "save")
    protected RedirectView save(@ModelAttribute ItemEditor editor,
                                BindingResult bindingResult) {

        return new RedirectView("");
    }

    @RequestMapping(path = "append")
    protected RedirectView append(@ModelAttribute ItemEditor editor,
                                  BindingResult bindingResult,
                                  @RequestParam(name = "attribute") String attributeName,
                                  @RequestParam(name = "structure") String structureName) {


//        maintenance.append(attributeName, structureName);

        return new RedirectView("");
    }
}
