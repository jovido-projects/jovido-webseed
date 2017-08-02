package biz.jovido.seed.content;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/admin/item/")
@SessionAttributes(types = ItemAdministration.class)
public class ItemEditorController {

    @Autowired
    private ItemService itemService;

    @ModelAttribute
    protected ItemAdministration administration() {
        ItemAdministration administration = new ItemAdministration();
        administration.setEditor(new ItemEditor());
        return administration;
    }

    @RequestMapping
    protected String index(@ModelAttribute ItemAdministration administration, BindingResult bindingResult) {

        return "admin/item/editor";
    }

    @RequestMapping(path = "create")
    protected RedirectView create(@ModelAttribute ItemAdministration administration,
                                  BindingResult bindingResult,
                                  @RequestParam(name = "structure") String structureName) {

        Item item = itemService.createItem(structureName, Locale.GERMAN);
        ItemEditor editor = administration.getEditor();
        editor.setItem(item);

        return new RedirectView("");
    }

    @RequestMapping(path = "append")
    protected RedirectView append(@ModelAttribute ItemAdministration administration,
                                  BindingResult bindingResult,
                                  @RequestParam(name = "attribute") String attributeName,
                                  @RequestParam(name = "structure") String structureName) {

        ItemEditor editor = administration.getEditor();
        Item item = editor.getItem();
        RelationPayload payload = (RelationPayload) item.getPayload(attributeName);
        Relation relation = payload.getValue();
        if (relation == null) {
            relation = new Relation();
            payload.setValue(relation);
        }

        List<Item> targets = relation.getRelatedItems();

        Item target = itemService.createItem(structureName, Locale.GERMAN);
        targets.add(target);

        return new RedirectView("");
    }

    @RequestMapping(path = "edit-related")
    protected RedirectView editRelated(@ModelAttribute ItemAdministration administration,
                                       BindingResult bindingResult,
                                       @RequestParam(name = "attribute") String attributeName,
                                       @RequestParam(name = "index") int index) {

        ItemEditor editor = administration.getEditor();

        Item item = editor.getItem();
        RelationPayload payload = (RelationPayload) item.getPayload(attributeName);
        Relation relation = payload.getValue();

        ItemEditor relatedEditor = new ItemEditor();
        ItemEditor.Parent parent = new ItemEditor.Parent();
        parent.setEditor(editor);
        parent.setAttributeName(attributeName);
        relatedEditor.setParent(parent);
        relatedEditor.setItem((relation.getRelatedItems().get(index)));

        administration.setEditor(relatedEditor);

        return new RedirectView("");
    }

    @RequestMapping(path = "close")
    protected RedirectView close(@ModelAttribute ItemAdministration administration,
                                 BindingResult bindingResult) {

        ItemEditor editor = administration.getEditor();
        ItemEditor.Parent parent = editor.getParent();
        if (parent != null) {
            ItemEditor parentEditor = parent.getEditor();
            administration.setEditor(parentEditor);
        }

        return new RedirectView("");
    }

    @RequestMapping(path = "save")
    protected RedirectView save(@ModelAttribute ItemAdministration administration,
                                BindingResult bindingResult) {

        return new RedirectView("");
    }
}
