package biz.jovido.seed.content;

import biz.jovido.seed.ui.Breadcrumb;
import biz.jovido.seed.ui.Text;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

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

        Item item = editor.getItem();
        if (item != null) {
            String label = itemService.getLabelText(item);
            Text text = new Text();
            text.setDefaultMessage(label);
            breadcrumbs.add(new Breadcrumb(text));
        }

        return breadcrumbs;
    }

    @InitBinder("itemEditor")
    protected void initBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("item*");
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest request, Exception e) {

        ModelAndView mav = new ModelAndView();
        mav.addObject("e", e);

        String[] stackFrames = ExceptionUtils.getStackFrames(e);
        String message = ExceptionUtils.getMessage(e);
        mav.addObject("frames", Arrays.asList(stackFrames));
        mav.addObject("message", message);
        mav.addObject("url", request.getRequestURL());
        mav.setViewName("admin/error");

        e.printStackTrace();

        return mav;
    }

    @ModelAttribute
    protected ItemEditor editor(@RequestParam(name = "loaded", required = false) Long itemId, HttpServletRequest request) {
        ItemEditor editor = new ItemEditor(itemService);

        Object x = request.getParameterMap();
        if (itemId != null) {
            Item item = itemService.getItem(itemId);
            editor.setItem(item);
        }

        return editor;
    }

    private String redirect(ItemEditor editor) {
        Item item = editor.getItem();
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

    @RequestMapping
    protected String index(@ModelAttribute ItemEditor editor,
                           @RequestParam(name = "id", required = false) Long itemId,
                           BindingResult editorBinding) {

        Item item = editor.getItem();
        if (item == null) {

            if (itemId != null) {
                return "redirect:edit?id=" + itemId;
            }
        }

        editor.refresh();

        return "admin/item/editor-page";
    }

    @RequestMapping(path = "/create")
    protected String create(@ModelAttribute ItemEditor editor,
                            BindingResult editorBinding,
                            @RequestParam(name = "structure") String structureName) {

        Item item = itemService.createItem(structureName);
        editor.setItem(item);

        return redirect(editor);
    }

    @RequestMapping(path = "edit")
    protected String edit(@ModelAttribute ItemEditor editor,
                          BindingResult editorBinding,
                          @RequestParam(name = "id") Long itemId) {

        Item item = itemService.getItem(itemId);
        itemService.refrehItem(item);
        editor.setItem(item);

        return redirect(editor);
    }

    @RequestMapping(path = "save")
    protected String save(@ModelAttribute ItemEditor editor,
                          BindingResult editorBinding) {

        Item item = editor.getItem();
        item = itemService.saveItem(item);
        editor.setItem(item);

        return redirect(editor);
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
                            @RequestParam(name = "item") UUID itemUuid,
                            @RequestParam(name = "attribute") String attributeName,
                            @RequestParam(name = "structure") String structureName) {

        Item item = itemService.findItem(editor.getItem(), itemUuid);
        Item embeddedItem = itemService.createEmbeddedItem(structureName);
        Structure structure = itemService.getStructure(item);
        Attribute attribute = structure.getAttribute(attributeName);
        ItemPayload payload = (ItemPayload) attribute.createPayload();
        payload.setItem(embeddedItem);
        PayloadGroup payloadGroup = item.getPayloadGroup(attributeName);
        payloadGroup.addPayload(payload);

        return redirect(editor);
    }

    @Deprecated
    @RequestMapping(path = "append", params = {"!structure"})
    protected String append(@ModelAttribute ItemEditor editor,
                            BindingResult editorBinding,
                            @RequestParam(name = "item") UUID itemUuid,
                            @RequestParam(name = "attribute") String attributeName) {

        Item item = itemService.findItem(editor.getItem(), itemUuid);
        Structure structure = itemService.getStructure(item);
        Attribute attribute = structure.getAttribute(attributeName);
        Payload payload = attribute.createPayload();
        PayloadGroup payloadGroup = item.getPayloadGroup(attributeName);
        payloadGroup.addPayload(payload);

        return redirect(editor);
    }

    @RequestMapping(path = "move-payload-up")
    protected String movePayloadUp(@ModelAttribute ItemEditor editor,
                                   BindingResult editorBinding,
                                   @RequestParam(name = "payload") UUID payloadUuid) {

        Payload payload = itemService.findPayload(editor.getItem(), payloadUuid);
        PayloadGroup payloadGroup = payload.getGroup();
        Item item = payloadGroup.getItem();
        int index = payload.getOrdinal();
        String attributeName = payloadGroup.getAttributeName();
        Payload previous = itemService.getPayload(item, attributeName, index - 1);
        payload.setOrdinal(previous.getOrdinal());
        previous.setOrdinal(index);

        return redirect(editor);
    }

    @RequestMapping(path = "move-payload-down")
    protected String movePayloadDown(@ModelAttribute ItemEditor editor,
                                     BindingResult editorBinding,
                                     @RequestParam(name = "payload") UUID payloadUuid) {

        Payload payload = itemService.findPayload(editor.getItem(), payloadUuid);
        PayloadGroup payloadGroup = payload.getGroup();
        Item item = payloadGroup.getItem();
        int index = payload.getOrdinal();
        String attributeName = payloadGroup.getAttributeName();
        Payload next = itemService.getPayload(item, attributeName, index + 1);
        payload.setOrdinal(next.getOrdinal());
        next.setOrdinal(index);

        return redirect(editor);
    }

    @RequestMapping(path = "remove-payload")
    protected String removePayload(@ModelAttribute ItemEditor editor,
                                   BindingResult editorBinding,
                                   @RequestParam(name = "payload") UUID payloadUuid) {

        Payload payload = itemService.findPayload(editor.getItem(), payloadUuid);
        PayloadGroup payloadGroup = payload.getGroup();
        payloadGroup.removePayload(payload);

        return redirect(editor);
    }

    @RequestMapping(path = "expand-payload")
    protected String expandPayload(@ModelAttribute ItemEditor editor,
                                   BindingResult editorBinding,
                                   @RequestParam(name = "payload") UUID payloadUuid) {

        Payload payload = itemService.findPayload(editor.getItem(), payloadUuid);
        ItemEditor.PayloadField field = editor.findField(payload);
        field.setCompressed(false);

        return redirect(editor);
    }


    @RequestMapping(path = "compress-payload")
    protected String compressPayload(@ModelAttribute ItemEditor editor,
                                     BindingResult editorBinding,
                                     @RequestParam(name = "payload") UUID payloadUuid) {

        Payload payload = itemService.findPayload(editor.getItem(), payloadUuid);
        ItemEditor.PayloadField field = editor.findField(payload);
        field.setCompressed(true);

        return redirect(editor);
    }

    @Transactional
//    @PostMapping(path = "upload-image", params = {"type=image"})
    @PostMapping(path = "upload-image")
    protected String uploadImage(@ModelAttribute ItemEditor editor,
                                 BindingResult editorBinding,
                                 @RequestParam(name = "token") String token,
                                 @RequestParam(name = "payload") UUID payloadUuid,
                                 HttpServletRequest request) throws IOException, ServletException {

        ImagePayload payload = (ImagePayload) itemService.findPayload(editor.getItem(), payloadUuid);
        Image image = (Image) payload.getImage();

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
                                 BindingResult editorBinding,
                                 @RequestParam(name = "payload") UUID payloadUuid,
                                 HttpServletRequest request) throws IOException, ServletException {

        ImagePayload payload = (ImagePayload) itemService.findPayload(editor.getItem(), payloadUuid);
        payload.setImage(null);

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
