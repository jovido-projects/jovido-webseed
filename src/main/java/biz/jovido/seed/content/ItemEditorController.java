package biz.jovido.seed.content;

import biz.jovido.seed.Slugifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author Stephan Grundner
 */
@Controller
@RequestMapping(path = "/admin/item/")
@SessionAttributes(types = {ItemEditor.class})
public class ItemEditorController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private AssetService assetService;

    @Autowired
    private HierarchyService hierarchyService;

    @ModelAttribute("breadcrumbs")
    protected List<Breadcrumb> breadcrumbs(@ModelAttribute ItemEditor editor) {
        List<Breadcrumb> breadcrumbs = new ArrayList<>();
        breadcrumbs.add(new Breadcrumb("Home", "/"));
        breadcrumbs.add(new Breadcrumb("Administration", "/admin/"));
        breadcrumbs.add(new Breadcrumb("Items", "/admin/items/"));

//        String breadcrumbText = itemService.getLabel(editor.getItem()).toString();
//        breadcrumbs.add(new Breadcrumb(breadcrumbText));
        Item item = editor.getItem();
        if (item != null) {
            String label = itemService.getLabelText(item);
            breadcrumbs.add(new Breadcrumb(label));
        }

        return breadcrumbs;
    }

    @ModelAttribute
    protected ItemEditor editor(@RequestParam(name = "id", required = false) Long itemId,
                                @RequestParam(name = "new", required = false) String structureName) {
        ItemEditor editor = new ItemEditor();

        if (itemId != null) {
            Item item = itemService.getItem(itemId);
            editor.setItem(item);
        } else if (StringUtils.hasText(structureName)) {
            Item item = itemService.createItem(structureName);
            editor.setItem(item);
        }

        return editor;
    }

    private String redirect(Item item) {
        if (item != null) {
            Long id = item.getId();
            if (id != null) {
                return String.format("redirect:?id=%d", id);
            } else {
                Structure structure = itemService.getStructure(item);
                if (structure != null) {
                    return String.format("redirect:?new=%s", structure.getName());
                }
            }
        }

        return "redirect:/admin/items/";
    }

    private String redirect(ItemEditor editor) {
        return redirect(editor.getItem());
    }

    @RequestMapping
    protected String index(@ModelAttribute ItemEditor editor,
                           BindingResult bindingResult,
                           Model model) {

        return "admin/item/editor";
    }

    @RequestMapping(path = "/create")
    protected String create(@ModelAttribute ItemEditor editor,
                            BindingResult bindingResult,
                            @RequestParam(name = "structure") String structureName) {

        Item item = itemService.createItem(structureName);
        editor.setItem(item);

        return redirect(item);
    }

//    @ExceptionHandler(Exception.class)
//    protected ModelAndView handleError(Exception e, ModelAndView modelAndView) {
//
//        modelAndView.addObject("e", e);
//        modelAndView.setViewName("admin/exception");
//        return modelAndView;
//    }

    @RequestMapping(path = "edit")
    protected String edit(@ModelAttribute ItemEditor editor,
                          @RequestParam(name = "item") Long itemId,
                          BindingResult bindingResult) {

        Item item = itemService.getItem(itemId);
        editor.setItem(item);

        return redirect(item);
    }

    @RequestMapping(path = "save")
    protected String save(@ModelAttribute ItemEditor editor,
                          BindingResult bindingResult) {

        Item item = editor.getItem();
        item = itemService.saveItem(item);
        editor.setItem(item);

        return redirect(item);
    }

    @RequestMapping(path = "close")
    protected String close(@ModelAttribute ItemEditor editor,
                           BindingResult bindingResult) {

        editor.setItem(null);

        return "redirect:/admin/items";
    }

//    @RequestMapping(path = "append", params = {"structure"})
//    protected String append(@ModelAttribute ItemEditor editor,
//                            @RequestParam(name = "nested-path") String nestedPath,
//                            @RequestParam(name = "attribute") String attributeName,
//                            @RequestParam(name = "structure") String typeName,
//                            BindingResult bindingResult) {
//
//        String propertyPath = String.format("%s.sequences[%s]", nestedPath, attributeName);
//        Sequence sequence = (Sequence) editor.getPropertyValue(propertyPath);
//        Attribute attribute = itemService.getAttribute(sequence);
//        RelationPayload payload = (RelationPayload) attribute.createPayload();
//        Item target = itemService.createEmbeddedItem(typeName, 1);
////        target.setLeaf(null);
//        Relation relation = payload.getRelation();
//        relation.setTarget(target);
//        sequence.addPayload(payload);
//
//        return "redirect:";
//    }

    @RequestMapping(path = "append", params = {"structure"})
    protected String append(@ModelAttribute ItemEditor editor,
                            @RequestParam(name = "nested-path") String nestedPath,
                            @RequestParam(name = "attribute") String attributeName,
                            @RequestParam(name = "structure") String structureName,
                            BindingResult bindingResult) {

        String propertyPath = String.format("%s.sequences[%s]", nestedPath, attributeName);
        Sequence sequence = (Sequence) editor.getPropertyValue(propertyPath);

        Item item = itemService.createEmbeddedItem(structureName);
        Attribute attribute = itemService.getAttribute(sequence);
        ItemPayload payload = (ItemPayload) attribute.createPayload();
        payload.setItem(item);
        sequence.addPayload(payload);

        return redirect(item);
    }

    @RequestMapping(path = "append", params = {"!structure"})
    protected String append(@ModelAttribute ItemEditor editor,
                            @RequestParam(name = "nested-path") String nestedPath,
                            @RequestParam(name = "attribute") String attributeName,
                            BindingResult bindingResult) {

        String propertyPath = String.format("%s.sequences[%s]", nestedPath, attributeName);
        Sequence sequence = (Sequence) editor.getPropertyValue(propertyPath);
        Attribute attribute = itemService.getAttribute(sequence);
        Payload payload = attribute.createPayload();
        sequence.addPayload(payload);

        return redirect(editor);
    }

    @RequestMapping(path = "move-payload-up")
    protected String movePayloadUp(@ModelAttribute ItemEditor editor,
                                   @RequestParam(name = "nested-path") String nestedPath,
                                   @RequestParam(name = "attribute") String attributeName,
                                   @RequestParam(name = "index") int index,
                                   BindingResult bindingResult) {

        String propertyPath = String.format("%s.sequences[%s]", nestedPath, attributeName);
        Sequence sequence = (Sequence) editor.getPropertyValue(propertyPath);
        sequence.movePayload(index, index - 1);

        return redirect(editor);
    }

    @RequestMapping(path = "move-payload-down")
    protected String movePayloadDown(@ModelAttribute ItemEditor editor,
                                     @RequestParam(name = "nested-path") String nestedPath,
                                     @RequestParam(name = "attribute") String attributeName,
                                     @RequestParam(name = "index") int index,
                                     BindingResult bindingResult) {

        String propertyPath = String.format("%s.sequences[%s]", nestedPath, attributeName);
        Sequence sequence = (Sequence) editor.getPropertyValue(propertyPath);
        sequence.movePayload(index, index + 1);

        return redirect(editor);
    }

    @RequestMapping(path = "remove-payload")
    protected String removePayload(@ModelAttribute ItemEditor editor,
                                   @RequestParam(name = "nested-path") String nestedPath,
                                   @RequestParam(name = "attribute") String attributeName,
                                   @RequestParam(name = "index") int index,
                                   BindingResult bindingResult) {

        String propertyPath = String.format("%s.sequences[%s]", nestedPath, attributeName);
        Sequence sequence = (Sequence) editor.getPropertyValue(propertyPath);
        sequence.removePayload(index);

        return redirect(editor);
    }

    @Transactional
//    @PostMapping(path = "upload-image", params = {"type=image"})
    @PostMapping(path = "upload-image")
    protected String uploadImage(@ModelAttribute ItemEditor editor,
                                 @RequestParam(name = "token") String token,
                                 @RequestParam(name = "nested-path") String nestedPath,
                                 @RequestParam(name = "attribute") String attributeName,
                                 @RequestParam(name = "index") int index,
                                 HttpServletRequest request) throws IOException, ServletException {

        String propertyPath = String.format("%s.sequences[%s]", nestedPath, attributeName);
        Sequence sequence = (Sequence) editor.getPropertyValue(propertyPath);
        ImagePayload payload = (ImagePayload) sequence.getPayload(index);

        Image image = payload.getImage();
        if (image == null) {
            image = new Image();
            payload.setImage(image);
        }

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getMultiFileMap().getFirst("file-" + token);

        File fileOnDisk = new File("assets/" + image.getUuid() + ".asset");

        try (InputStream inputStream = file.getInputStream()) {
            try (OutputStream outputStream = new FileOutputStream(fileOnDisk)) {
                FileCopyUtils.copy(inputStream, outputStream);
            }
        }

        image.setFileName(file.getOriginalFilename());
        image = (Image) assetService.saveAsset(image);
//        payload.setImage(image);


        return redirect(editor);
    }

    @Transactional
    @PostMapping(path = "remove-image")
    protected String removeImage(@ModelAttribute ItemEditor editor,
                                 @RequestParam(name = "nested-path") String nestedPath,
                                 @RequestParam(name = "attribute") String attributeName,
                                 @RequestParam(name = "index") int index,
                                 HttpServletRequest request) throws IOException, ServletException {

        String propertyPath = String.format("%s.sequences[%s]", nestedPath, attributeName);
        Sequence sequence = (Sequence) editor.getPropertyValue(propertyPath);
        ImagePayload payload = (ImagePayload) sequence.getPayload(index);

        payload.setImage(null);

        return redirect(editor);
    }

    @PostMapping(path = "publish")
    protected String publish(@ModelAttribute ItemEditor editor,
                             BindingResult bindingResult) {

        Item item = editor.getItem();
        Item current = itemService.publishItem(item);

        editor.setItem(current);

        return redirect(editor);
    }

    @PostMapping(path = "add-node", params = {})
    protected String addNode(@ModelAttribute ItemEditor editor,
                             BindingResult bindingResult,
                             @RequestParam(name = "hierarchy") Long hierarchyId,
                             @RequestParam(name = "parent", required = false) Long parentId) {

        Item item = editor.getItem();
        Leaf leaf = item.getLeaf();
        Locale locale = item.getLocale();
        Assert.notNull(locale);

        Hierarchy hierarchy = hierarchyService.getHierarchy(hierarchyId);

        Node node = new Node();
        node.setLeaf(item.getLeaf());

        if (parentId != null) {
            Node parent = hierarchyService.getNode(parentId);
            node.setParent(parent);
        }

        hierarchy.addNode(node);
        node = hierarchyService.saveNode(node);

        leaf.addNode(node);

        return redirect(editor);
    }


    @PostMapping(path = "move-node-up")
    protected String moveNodeUp(@ModelAttribute ItemEditor editor,
                                BindingResult bindingResult,
                                @RequestParam(name = "node") Long nodeId) {

        Node node = hierarchyService.getNode(nodeId);
        Hierarchy hierarchy = node.getHierarchy();
        Node previousSibling = node.getPreviousSibling();
        hierarchyService.swapNodes(node, previousSibling);
        hierarchyService.saveNode(node);
        hierarchyService.saveHierarchy(hierarchy);

        return redirect(editor);
    }

    @PostMapping(path = "move-node-down")
    protected String moveNodeDown(@ModelAttribute ItemEditor editor,
                                  BindingResult bindingResult,
                                  @RequestParam(name = "node") Long nodeId) {

        Node node = hierarchyService.getNode(nodeId);
        Node nextSibling = node.getNextSibling();
        hierarchyService.swapNodes(node, nextSibling);
        hierarchyService.saveNode(node);

        return redirect(editor);
    }

    @PostMapping(path = "remove-node")
    protected String removeNode(@ModelAttribute ItemEditor editor,
                                BindingResult bindingResult,
                                @RequestParam(name = "node") Long nodeId) {

        Node node = hierarchyService.getNode(nodeId);
        hierarchyService.deleteNode(node);

        return redirect(editor);
    }

    @PostMapping(path = "generate-path", params = {})
    protected String generatePath(@ModelAttribute ItemEditor editor,
                                  BindingResult bindingResult) {

        Item item = editor.getItem();


        String labelText = itemService.getLabelText(item);
        Slugifier slugifier = new Slugifier();
        String slug = slugifier.slugify(labelText, '-');
        item.setPath(slug);


        return redirect(editor);
    }
}
