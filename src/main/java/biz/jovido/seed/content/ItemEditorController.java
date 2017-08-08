package biz.jovido.seed.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
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

        editor.save();

        return new RedirectView("");
    }

    @RequestMapping(path = "append")
    protected RedirectView append(@ModelAttribute ItemEditor editor,
                                  BindingResult bindingResult,
                                  @RequestParam(name = "attribute") String attributeName,
                                  @RequestParam(name = "structure") String structureName) {

        Item item = editor.getItem();
        RelationPayload payload = (RelationPayload) item.getPayload(attributeName);
        Relation relation = payload.getValue();
        if (relation == null) {
            relation = new Relation();
            payload.setValue(relation);
        }

        Item target = itemService.createItem(structureName, Locale.GERMAN);
        relation.addTarget(target);

        editor.append(attributeName, structureName);

        return new RedirectView("");
    }
}
