package biz.jovido.seed.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Stephan Grundner
 */
@Controller
@RequestMapping(path = "/admin/hierarchy/")
@SessionAttributes(types = HierarchyForm.class)
public class HierarchyController {

    @Autowired
    private HierarchyService hierarchyService;

    @Autowired
    private ItemService itemService;

    @ModelAttribute
    protected HierarchyForm form() {
        return new HierarchyForm();
    }

    protected String redirect(HierarchyForm form) {

        Item item = form.getItem();
        return "redirect:?item=" + item.getId();
    }

    @RequestMapping
    protected String index(
            @ModelAttribute HierarchyForm form,
            @RequestParam(name = "item") Long itemId) {

        Item item = itemService.getItem(itemId);
        form.setItem(item);

        return "admin/item/hierarchies";
    }

    @RequestMapping(path = "add-node", params = {})
    protected String addNode(@ModelAttribute HierarchyForm form,
                             BindingResult bindingResult,
                             @RequestParam(name = "branch") Long branchId,
                             @RequestParam(name = "parent-uuid", required = false) UUID parentUuid) {

        Item item = form.getItem();
        ItemHistory history = item.getHistory();
        Locale locale = item.getLocale();
        Assert.notNull(locale);

        Node node = new Node();
        node.setUuid(UUID.randomUUID());
        node.setHistory(item.getHistory());

        Branch branch = hierarchyService.getBranch(branchId);

        Node parent = null;

        if (parentUuid != null) {
            parent = branch.getNodes().stream()
                    .filter(Objects::nonNull)
                    .filter(it -> it.getUuid() != null)
                    .filter(it -> it.getUuid().equals(parentUuid))
                    .findFirst()
                    .orElse(null);
        }

        node.setParent(parent);

        node.setBranch(branch);
        node = hierarchyService.saveNode(node);

        history.addNode(node);
        branch.addNode(node);

        hierarchyService.saveBranch(branch);

        return redirect(form);
    }
}
