package biz.jovido.seed.content.web;

import biz.jovido.seed.PropertyUtils;
import biz.jovido.seed.content.ItemService;
import biz.jovido.seed.content.model.Item;
import biz.jovido.seed.content.model.Relation;
import biz.jovido.seed.content.model.RelationPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Locale;

/**
 * @author Stephan Grundner
 */
@Controller
@RequestMapping("/admin/items")
@SessionAttributes(types = {ItemEditor.class})
public class EditorController {

    private static final String REDIRECT_TO_INDEX = "redirect:";

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
    protected String create(@ModelAttribute ItemEditor editor,
                            @RequestParam(name = "structure") String structureName,
                            BindingResult bindingResult) {

        Locale locale = LocaleContextHolder.getLocale();
        Item item = itemService.createItem(structureName, locale);
        editor.setItem(item);

        return REDIRECT_TO_INDEX;
    }

    @RequestMapping(path = "save")
    protected String save(@ModelAttribute ItemEditor editor,
                          BindingResult bindingResult) {

        return REDIRECT_TO_INDEX;
    }

    @RequestMapping(path = "add-relation")
    protected String addRelation(@ModelAttribute ItemEditor editor,
                                 @RequestParam(name = "nested-path") String nestedPath,
                                 @RequestParam(name = "attribute") String attributeName,
                                 @RequestParam(name = "structure") String structureName,
                                 BindingResult bindingResult) {

        Item item = editor.getItem();
//        RelationPayload payload = (RelationPayload) item.getPayload(attributeName);
        String propertyPath = String.format("%sfields[%s]", nestedPath, attributeName);
//        RelationPayload payload = (RelationPayload) PropertyUtils.getPropertyValue(editor, propertyPath);
        RelationsField relationsField = (RelationsField) PropertyUtils.getPropertyValue(editor, propertyPath);
        RelationPayload payload = (RelationPayload) relationsField.getPayload();
        Relation relation = payload.getValue();
        if (relation == null) {
            relation = new Relation();
            payload.setValue(relation);
        }
        Item target = itemService.createItem(structureName, LocaleContextHolder.getLocale());
        relation.addTarget(target);

        return REDIRECT_TO_INDEX;
    }
}
