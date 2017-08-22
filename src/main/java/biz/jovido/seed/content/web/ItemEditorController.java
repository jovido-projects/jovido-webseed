package biz.jovido.seed.content.web;

import biz.jovido.seed.util.PropertyUtils;
import biz.jovido.seed.content.service.ItemService;
import biz.jovido.seed.content.model.Item;
import biz.jovido.seed.content.model.Relation;
import biz.jovido.seed.content.model.RelationPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * @author Stephan Grundner
 */
@Controller
@RequestMapping("/admin/item")
@SessionAttributes(types = {ItemEditor.class})
public class ItemEditorController {

    @Autowired
    private ItemService itemService;

    @InitBinder
    private void initBinder(WebDataBinder dataBinder) {}

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
    protected String create(@ModelAttribute ItemEditor editor,
                            @RequestParam(name = "structure") String structureName,
                            BindingResult bindingResult) {

        Item item = itemService.createItem(structureName, Locale.GERMAN);
        editor.setItem(item);

        return "redirect:";
    }

    @RequestMapping(path = "edit")
    protected String edit(@ModelAttribute ItemEditor editor,
                            @RequestParam(name = "item") Long itemId,
                            BindingResult bindingResult) {

        Item item = itemService.getItem(itemId);
        editor.setItem(item);

        return "redirect:";
    }

    @RequestMapping(path = "save")
    protected String save(@ModelAttribute ItemEditor editor,
                          BindingResult bindingResult) {

        Item item = editor.getItem();
        item = itemService.saveItem(item);
        editor.setItem(item);

        return "redirect:";
    }

    @RequestMapping(path = "close")
    protected String close(@ModelAttribute ItemEditor editor,
                          BindingResult bindingResult) {

        editor.setItem(null);

        return "redirect:/admin/items";
    }

    @RequestMapping(path = "add-relation")
    protected String addRelation(@ModelAttribute ItemEditor editor,
                                 @RequestParam(name = "nested-path") String nestedPath,
                                 @RequestParam(name = "attribute") String attributeName,
                                 @RequestParam(name = "structure") String structureName,
                                 BindingResult bindingResult) {

        String propertyPath = String.format("%s.payloads[%s]", nestedPath, attributeName);
        RelationPayload payload = (RelationPayload) PropertyUtils.getPropertyValue(editor, propertyPath);
        Relation relation = payload.getValue();
        if (relation == null) {
            relation = new Relation();
            payload.setValue(relation);
        }
        Item target = itemService.createItem(structureName, LocaleContextHolder.getLocale());
        target.setChronicle(null);
        relation.getTargets().add(target);

        return "redirect:";
    }

    @RequestMapping(path = "remove-relation")
    protected String removeRelation(@ModelAttribute ItemEditor editor,
                                 @RequestParam(name = "nested-path") String nestedPath,
                                 @RequestParam(name = "attribute") String attributeName,
                                 @RequestParam(name = "index") int index,
                                 BindingResult bindingResult) {

        String propertyPath = String.format("%s.payloads[%s]", nestedPath, attributeName);
        RelationPayload payload = (RelationPayload) PropertyUtils.getPropertyValue(editor, propertyPath);
        Relation relation = payload.getValue();
        relation.getTargets().remove(index);

        return "redirect:";
    }

    @RequestMapping(path = "move-relation-up")
    protected String moveRelationUp(@ModelAttribute ItemEditor editor,
                                    @RequestParam(name = "nested-path") String nestedPath,
                                    @RequestParam(name = "attribute") String attributeName,
                                    @RequestParam(name = "index") int index,
                                    BindingResult bindingResult) {

        String propertyPath = String.format("%s.payloads[%s]", nestedPath, attributeName);
        RelationPayload payload = (RelationPayload) PropertyUtils.getPropertyValue(editor, propertyPath);
        Relation relation = payload.getValue();
        List<Item> targets = relation.getTargets();

        Collections.swap(targets, index, index - 1);

        return "redirect:";
    }

    @RequestMapping(path = "move-relation-down")
    protected String moveRelationDown(@ModelAttribute ItemEditor editor,
                                    @RequestParam(name = "nested-path") String nestedPath,
                                    @RequestParam(name = "attribute") String attributeName,
                                    @RequestParam(name = "index") int index,
                                    BindingResult bindingResult) {

        String propertyPath = String.format("%s.payloads[%s]", nestedPath, attributeName);
        RelationPayload payload = (RelationPayload) PropertyUtils.getPropertyValue(editor, propertyPath);
        Relation relation = payload.getValue();
        List<Item> targets = relation.getTargets();

        Collections.swap(targets, index, index + 1);

        return "redirect:";
    }
}
