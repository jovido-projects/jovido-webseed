package biz.jovido.seed.content.web;

import biz.jovido.seed.content.model.*;
import biz.jovido.seed.content.service.ItemService;
import biz.jovido.seed.util.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

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
    private void initBinder(WebDataBinder dataBinder) {
    }

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
                            @RequestParam(name = "structure") String typeName,
                            BindingResult bindingResult) {

        Item item = itemService.createItem(typeName, 1, Locale.GERMAN);
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

    @RequestMapping(path = "append", params = {"structure"})
    protected String append(@ModelAttribute ItemEditor editor,
                            @RequestParam(name = "nested-path") String nestedPath,
                            @RequestParam(name = "attribute") String attributeName,
                            @RequestParam(name = "structure") String typeName,
                            BindingResult bindingResult) {

        String propertyPath = String.format("%s.sequences[%s]", nestedPath, attributeName);
        Sequence sequence = (Sequence) PropertyUtils.getPropertyValue(editor, propertyPath);
        Attribute attribute = itemService.getAttribute(sequence);
        RelationPayload payload = (RelationPayload) attribute.createPayload();
        Item target = itemService.createEmbeddedItem(typeName, 1);
//        target.setChronicle(null);
        Relation relation = payload.getRelation();
        relation.setTarget(target);
        sequence.addPayload(payload);

        return "redirect:";
    }

    @RequestMapping(path = "append", params = {"!structure"})
    protected String append(@ModelAttribute ItemEditor editor,
                            @RequestParam(name = "nested-path") String nestedPath,
                            @RequestParam(name = "attribute") String attributeName,
                            BindingResult bindingResult) {

        String propertyPath = String.format("%s.sequences[%s]", nestedPath, attributeName);
        Sequence sequence = (Sequence) PropertyUtils.getPropertyValue(editor, propertyPath);
        Attribute attribute = itemService.getAttribute(sequence);
        Payload payload = attribute.createPayload();
        sequence.addPayload(payload);

        return "redirect:";
    }

    @RequestMapping(path = "move-payload-up")
    protected String movePayloadUp(@ModelAttribute ItemEditor editor,
                                   @RequestParam(name = "nested-path") String nestedPath,
                                   @RequestParam(name = "attribute") String attributeName,
                                   @RequestParam(name = "index") int index,
                                   BindingResult bindingResult) {

        String propertyPath = String.format("%s.sequences[%s]", nestedPath, attributeName);
        Sequence sequence = (Sequence) PropertyUtils.getPropertyValue(editor, propertyPath);
        sequence.movePayload(index, index - 1);

        return "redirect:";
    }

    @RequestMapping(path = "move-payload-down")
    protected String movePayloadDown(@ModelAttribute ItemEditor editor,
                                     @RequestParam(name = "nested-path") String nestedPath,
                                     @RequestParam(name = "attribute") String attributeName,
                                     @RequestParam(name = "index") int index,
                                     BindingResult bindingResult) {

        String propertyPath = String.format("%s.sequences[%s]", nestedPath, attributeName);
        Sequence sequence = (Sequence) PropertyUtils.getPropertyValue(editor, propertyPath);
        sequence.movePayload(index, index + 1);

        return "redirect:";
    }

    @RequestMapping(path = "remove-payload")
    protected String removePayload(@ModelAttribute ItemEditor editor,
                                   @RequestParam(name = "nested-path") String nestedPath,
                                   @RequestParam(name = "attribute") String attributeName,
                                   @RequestParam(name = "index") int index,
                                   BindingResult bindingResult) {

        String propertyPath = String.format("%s.sequences[%s]", nestedPath, attributeName);
        Sequence sequence = (Sequence) PropertyUtils.getPropertyValue(editor, propertyPath);
        sequence.removePayload(index);

        return "redirect:";
    }

    @RequestMapping(path = "add-node")
    protected String addNode(@ModelAttribute ItemEditor editor,
                             @RequestParam(name = "hierarchy") String hierarchyName,
                             @RequestParam(name = "parent-node", required = false) Long parentNodeId,
                             BindingResult bindingResult) {

        Item item = editor.getItem();
        Locale locale = item.getBundle().getLocale();

        Node node = new Node();
        node.setBundle(item.getBundle());

        if (parentNodeId == null || parentNodeId == -1) {
            Root root = itemService.getOrCreateRoot(hierarchyName, locale);
            root.addNode(node);

        } else {
            Node parentNode = itemService.getNode(parentNodeId);
            parentNode.addChild(node);

        }

        node = itemService.saveNode(node);

        item.addNode(node);

        return "redirect:";
    }


//    @RequestMapping(path = "add-relation")
//    protected String addRelation(@ModelAttribute ItemEditor editor,
//                                 @RequestParam(name = "nested-path") String nestedPath,
//                                 @RequestParam(name = "attribute") String attributeName,
//                                 @RequestParam(name = "structure") String structureName,
//                                 BindingResult bindingResult) {
//
//        String propertyPath = String.format("%s.payloads[%s]", nestedPath, attributeName);
//        RelationPayload payload = (RelationPayload) PropertyUtils.getPropertyValue(editor, propertyPath);
////        Relation relation = payload.getValue();
////        if (relation == null) {
////            relation = new Relation();
////            payload.setValue(relation);
////        }
////        Item target = itemService.createItem(structureName, LocaleContextHolder.getLocale());
////        target.setChronicle(null);
////        relation.getTargets().add(target);
//
//        return "redirect:";
//    }

    @RequestMapping(path = "move-relation-up")
    protected String moveRelationUp(@ModelAttribute ItemEditor editor,
                                    @RequestParam(name = "nested-path") String nestedPath,
                                    @RequestParam(name = "attribute") String attributeName,
                                    @RequestParam(name = "index") int index,
                                    BindingResult bindingResult) {

        String propertyPath = String.format("%s.payloads[%s]", nestedPath, attributeName);
        RelationPayload payload = (RelationPayload) PropertyUtils.getPropertyValue(editor, propertyPath);
//        Relation relation = payload.getValue();
//        List<Item> targets = relation.getTargets();
//
//        Collections.swap(targets, index, index - 1);

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
//        Relation relation = payload.getValue();
//        List<Item> targets = relation.getTargets();
//
//        Collections.swap(targets, index, index + 1);

        return "redirect:";
    }
}
