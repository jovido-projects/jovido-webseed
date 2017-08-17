package biz.jovido.seed.content.web;

import biz.jovido.seed.content.ItemService;
import biz.jovido.seed.content.model.*;
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

    @RequestMapping(path = "append-payload", params = {"!structure"})
    protected RedirectView appendPayload(@ModelAttribute ItemEditor editor,
                                         BindingResult bindingResult,
                                         @RequestParam(name = "property") String propertyName) {

        Item item = editor.getItem();
        Property property = item.getProperty(propertyName);
        Structure structure = itemService.getStructure(item);
        Attribute attribute = structure.getAttribute(propertyName);
        property.addPayload(attribute.createPayload());

        return new RedirectView("");
    }

    @RequestMapping(path = "remove-payload")
    protected RedirectView removePayload(@ModelAttribute ItemEditor editor,
                                         BindingResult bindingResult,
                                         @RequestParam(name = "property") String propertyName,
                                         @RequestParam(name = "index") int index) {
        Item item = editor.getItem();
        Property property = item.getProperty(propertyName);
        property.removePayload(index);

        return new RedirectView("");
    }

    @RequestMapping(path = "append-payload")
    protected RedirectView appendPayload(@ModelAttribute ItemEditor editor,
                                  BindingResult bindingResult,
                                  @RequestParam(name = "property") String propertyName,
                                  @RequestParam(name = "structure") String structureName) {

        Item item = editor.getItem();
        Property property = item.getProperty(propertyName);

        RelationPayload payload = new RelationPayload();
        Relation relation = new Relation();

        Item target = itemService.createItem(structureName, Locale.GERMAN);
        relation.setTarget(target);
        payload.setValue(relation);
        property.addPayload(payload);

        return new RedirectView("");
    }
}
