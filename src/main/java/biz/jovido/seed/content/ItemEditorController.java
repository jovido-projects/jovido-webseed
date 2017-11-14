package biz.jovido.seed.content;

import biz.jovido.seed.ui.Breadcrumb;
import biz.jovido.seed.ui.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    private StructureService structureService;

    @ModelAttribute("breadcrumbs")
    protected List<Breadcrumb> breadcrumbs(@ModelAttribute ItemEditor editor) {
        List<Breadcrumb> breadcrumbs = new ArrayList<>();
        breadcrumbs.add(new Breadcrumb("seed.home", "/admin"));
        breadcrumbs.add(new Breadcrumb("seed.item.listing.title", "/admin/items"));

//        String breadcrumbText = itemService.getLabel(editor.getItem()).toString();
//        breadcrumbs.add(new Breadcrumb(breadcrumbText));
        Item item = editor.getItem();
        if (item != null) {
            String label = itemService.getLabelText(item);
            Text text = new Text();
            text.setDefaultMessage(label);
            breadcrumbs.add(new Breadcrumb(text));
        }

        return breadcrumbs;
    }

    @ModelAttribute
    protected ItemEditor editor(@RequestParam(name = "id", required = false) Long itemId,
                                @RequestParam(name = "new", required = false) String structureName) {
        ItemEditor editor = new ItemEditor();

        editor.setPayloadGroupTemplateProvider(new ItemEditor.PayloadGroupTemplateProvider() {
            @Override
            public String getTemplate(PayloadGroup payloadGroup) {
                Attribute attribute = itemService.getAttribute(payloadGroup);

                if (attribute instanceof ItemAttribute) {
                    return "admin/item/editor/item-payload-group";
                } else if (attribute instanceof YesNoAttribute) {
                    return "admin/item/editor/yesno-payload-group";
                } else {
                    return "admin/item/editor/payload-group";
                }
            }
        });

        editor.setPayloadTemplateProvider(new ItemEditor.PayloadTemplateProvider() {
            @Override
            public String getTemplate(Payload payload) {
                Attribute attribute = itemService.getAttribute(payload);

                if (attribute instanceof TextAttribute) {
                    boolean multiline = ((TextAttribute) attribute).isMultiline();
                    if (multiline) {
                        return "admin/item/editor/multiline-text-payload";
                    } else {
                        return "admin/item/editor/text-payload";
                    }
                } else if (attribute instanceof ItemAttribute) {
                    return "admin/item/editor/item-payload";
                } else if (attribute instanceof LinkAttribute) {
                    return "admin/item/editor/link-payload";
                } else if (attribute instanceof IconAttribute) {
                    return "admin/item/editor/icon-payload";
                } else if (attribute instanceof ImageAttribute) {
                    return "admin/item/editor/image-payload";
                } else if (attribute instanceof YesNoAttribute) {
                    return "admin/item/editor/yesno-payload";
                } else {
                    throw new RuntimeException("Unexpected attribute type: " + attribute.getClass());
                }
            }
        });

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
                           BindingResult editorBinding) {

        return "admin/item/editor-page";
    }

    @RequestMapping(path = "/create")
    protected String create(@ModelAttribute ItemEditor editor,
                            BindingResult editorBinding,
                            @RequestParam(name = "structure") String structureName) {

        Item item = itemService.createItem(structureName);
        editor.setItem(item);

        return redirect(item);
    }

    @RequestMapping(path = "edit")
    protected String edit(@ModelAttribute ItemEditor editor,
                          BindingResult editorBinding,
                          @RequestParam(name = "id") Long itemId) {

        Item item = itemService.getItem(itemId);
        editor.setItem(item);

        for (PayloadGroup payloadGroup : item.getPayloadGroups().values()) {
            for (Payload payload : payloadGroup.getPayloads()) {
                if (payload instanceof ItemRelation) {
                    Item nestedItem = ((ItemRelation) payload).getTarget();
                    ItemUtils.walkItem(nestedItem, new SimpleItemVisitor() {
                        @Override
                        public ItemVisitResult visitPayload(Payload payload) {
                            payload.setCompressed(true);
                            return super.visitPayload(payload);
                        }
                    });
                }
            }
        }

        return redirect(item);
    }

    @RequestMapping(path = "save")
    protected String save(@ModelAttribute ItemEditor editor,
                          BindingResult editorBinding) {

        Item item = editor.getItem();
        item = itemService.saveItem(item);
        editor.setItem(item);

        return redirect(item);
    }

    @RequestMapping(path = "close")
    protected String close(@ModelAttribute ItemEditor editor,
                           BindingResult editorBinding) {

        editor.setItem(null);

        return "redirect:/admin/items";
    }

    @RequestMapping(path = "append", params = {"structure"})
    protected String append(@ModelAttribute ItemEditor editor,
                            BindingResult editorBinding,
                            @RequestParam(name = "nested-path") String nestedPath,
                            @RequestParam(name = "attribute") String attributeName,
                            @RequestParam(name = "structure") String structureName) {

        String propertyPath = String.format("%s.payloadGroups[%s]", nestedPath, attributeName);
        PayloadGroup sequence = (PayloadGroup) editor.getPropertyValue(propertyPath);

        Item item = itemService.createEmbeddedItem(structureName);
        Attribute attribute = itemService.getAttribute(sequence);
        ItemRelation payload = (ItemRelation) attribute.createPayload();
        payload.setTarget(item);
        sequence.addPayload(payload);
        payload.setCompressed(false);

        return redirect(item);
    }

    @RequestMapping(path = "append", params = {"!structure"})
    protected String append(@ModelAttribute ItemEditor editor,
                            BindingResult editorBinding,
                            @RequestParam(name = "nested-path") String nestedPath,
                            @RequestParam(name = "attribute") String attributeName) {

        String propertyPath = String.format("%s.payloadGroups[%s]", nestedPath, attributeName);
        PayloadGroup sequence = (PayloadGroup) editor.getPropertyValue(propertyPath);
        Attribute attribute = itemService.getAttribute(sequence);
        Payload payload = attribute.createPayload();
        sequence.addPayload(payload);

        return redirect(editor);
    }

    @RequestMapping(path = "move-payload-up")
    protected String movePayloadUp(@ModelAttribute ItemEditor editor,
                                   BindingResult editorBinding,
                                   @RequestParam(name = "nested-path") String nestedPath,
                                   @RequestParam(name = "attribute") String attributeName,
                                   @RequestParam(name = "index") int index) {

        String propertyPath = String.format("%s.payloadGroups[%s]", nestedPath, attributeName);
        PayloadGroup sequence = (PayloadGroup) editor.getPropertyValue(propertyPath);
        sequence.movePayload(index, index - 1);

        return redirect(editor);
    }

    @RequestMapping(path = "move-payload-down")
    protected String movePayloadDown(@ModelAttribute ItemEditor editor,
                                     BindingResult editorBinding,
                                     @RequestParam(name = "nested-path") String nestedPath,
                                     @RequestParam(name = "attribute") String attributeName,
                                     @RequestParam(name = "index") int index) {

        String propertyPath = String.format("%s.payloadGroups[%s]", nestedPath, attributeName);
        PayloadGroup sequence = (PayloadGroup) editor.getPropertyValue(propertyPath);
        sequence.movePayload(index, index + 1);

        return redirect(editor);
    }

    @RequestMapping(path = "remove-payload")
    protected String removePayload(@ModelAttribute ItemEditor editor,
                                   BindingResult editorBinding,
                                   @RequestParam(name = "nested-path") String nestedPath,
                                   @RequestParam(name = "attribute") String attributeName,
                                   @RequestParam(name = "index") int index) {

        String propertyPath = String.format("%s.payloadGroups[%s]", nestedPath, attributeName);
        PayloadGroup sequence = (PayloadGroup) editor.getPropertyValue(propertyPath);
        sequence.removePayload(index);

        return redirect(editor);
    }

    @RequestMapping(path = "open-payload")
    protected String openPayload(@ModelAttribute ItemEditor editor,
                                 BindingResult editorBinding,
                                 @RequestParam(name = "nested-path") String nestedPath,
                                 @RequestParam(name = "attribute") String attributeName,
                                 @RequestParam(name = "index") int index) {

        String propertyPath = String.format("%s.payloadGroups[%s]", nestedPath, attributeName);
        PayloadGroup sequence = (PayloadGroup) editor.getPropertyValue(propertyPath);
        ItemRelation payload = (ItemRelation) sequence.getPayload(index);

        Item item = payload.getTarget();
        for (PayloadGroup payloadGroup : item.getPayloadGroups().values()) {
            for (Payload nestedPayload : payloadGroup.getPayloads()) {
                if (!( nestedPayload instanceof ItemRelation)) {
                    nestedPayload.setCompressed(false);
                }
            }
        }

        payload.setCompressed(false);

        return redirect(editor);
    }


    @RequestMapping(path = "close-payload")
    protected String closePayload(@ModelAttribute ItemEditor editor,
                                  BindingResult editorBinding,
                                  @RequestParam(name = "nested-path") String nestedPath,
                                  @RequestParam(name = "attribute") String attributeName,
                                  @RequestParam(name = "index") int index) {

        String propertyPath = String.format("%s.payloadGroups[%s]", nestedPath, attributeName);
        PayloadGroup sequence = (PayloadGroup) editor.getPropertyValue(propertyPath);
        ItemRelation payload = (ItemRelation) sequence.getPayload(index);
        payload.setCompressed(true);

        ItemUtils.walkPayload(payload, new SimpleItemVisitor() {
            @Override
            public ItemVisitResult visitPayload(Payload payload) {
                payload.setCompressed(true);
                return super.visitPayload(payload);
            }
        });

        return redirect(editor);
    }

    @Transactional
//    @PostMapping(path = "upload-image", params = {"type=image"})
    @PostMapping(path = "upload-image")
    protected String uploadImage(@ModelAttribute ItemEditor editor,
                                 BindingResult editorBinding,
                                 @RequestParam(name = "token") String token,
                                 @RequestParam(name = "nested-path") String nestedPath,
                                 @RequestParam(name = "attribute") String attributeName,
                                 @RequestParam(name = "index") int index,
                                 HttpServletRequest request) throws IOException, ServletException {

        String propertyPath = String.format("%s.payloadGroups[%s]", nestedPath, attributeName);
        PayloadGroup payloadGroup = (PayloadGroup) editor.getPropertyValue(propertyPath);
        ImageRelation relation = (ImageRelation) payloadGroup.getPayload(index);
        Image image = relation.getTarget();

        if (image == null) {
            image = new Image();
            relation.setTarget(image);
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
                                 BindingResult editorBinding,
                                 @RequestParam(name = "nested-path") String nestedPath,
                                 @RequestParam(name = "attribute") String attributeName,
                                 @RequestParam(name = "index") int index,
                                 HttpServletRequest request) throws IOException, ServletException {

        String propertyPath = String.format("%s.payloadGroups[%s]", nestedPath, attributeName);
        PayloadGroup payloadGroup = (PayloadGroup) editor.getPropertyValue(propertyPath);
        ImageRelation relation = (ImageRelation) payloadGroup.getPayload(index);
        Image image = (Image) relation.getTarget();

        relation.setTarget(null);

        return redirect(editor);
    }

    @PostMapping(path = "publish")
    protected String publish(@ModelAttribute ItemEditor editor,
                             BindingResult editorBinding) {

        Item item = editor.getItem();
        Item current = itemService.publishItem(item);

        editor.setItem(current);

        return redirect(editor);
    }

    @PostMapping(path = "add-node", params = {})
    protected String addNode(@ModelAttribute ItemEditor editor,
                             BindingResult editorBinding,
                             @RequestParam(name = "hierarchy") Long hierarchyId,
                             @RequestParam(name = "parent", required = false) Long parentId) {

        Item item = editor.getItem();
        Leaf leaf = item.getLeaf();

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
                                BindingResult editorBinding,
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
                                  BindingResult editorBinding,
                                  @RequestParam(name = "node") Long nodeId) {

        Node node = hierarchyService.getNode(nodeId);
        Node nextSibling = node.getNextSibling();
        hierarchyService.swapNodes(node, nextSibling);
        hierarchyService.saveNode(node);

        return redirect(editor);
    }

    @PostMapping(path = "remove-node")
    protected String removeNode(@ModelAttribute ItemEditor editor,
                                BindingResult editorBinding,
                                @RequestParam(name = "node") Long nodeId) {

        Node node = hierarchyService.getNode(nodeId);
        hierarchyService.deleteNode(node);

        return redirect(editor);
    }

    @PostMapping(path = "generate-path", params = {})
    protected String generatePath(@ModelAttribute ItemEditor editor,
                                  BindingResult editorBinding) {

        Item item = editor.getItem();


        String labelText = itemService.getLabelText(item);
        Slugifier slugifier = new Slugifier();
        String slug = slugifier.slugify(labelText, "-");
        item.setPath(slug);


        return redirect(editor);
    }

    @PostMapping(path = "change-locale", params = {})
    protected String changeLocale(@ModelAttribute ItemEditor editor,
                                  BindingResult editorBinding,
                                  @RequestParam(name = "locale") Locale locale) {
        Item item = editor.getItem();
        item.setLocale(locale);

        return redirect(editor);
    }
}
