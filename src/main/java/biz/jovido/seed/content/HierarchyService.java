package biz.jovido.seed.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Stephan Grundner
 */
@Service
public class HierarchyService {

    @Autowired
    private HierarchyRepository hierarchyRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private NodeRepository nodeRepository;

    @Autowired
    private EntityManager entityManager;

    public Hierarchy getHierarchy(String name) {
        return hierarchyRepository.findByName(name);
    }

    public Hierarchy saveHierarchy(Hierarchy hierarchy) {
        return hierarchyRepository.saveAndFlush(hierarchy);
    }

    public Hierarchy getOrCreateHierarchy(String name) {
        Hierarchy hierarchy = getHierarchy(name);
        if (hierarchy == null) {
            hierarchy = new Hierarchy();
            hierarchy.setName(name);
            hierarchy = hierarchyRepository.saveAndFlush(hierarchy);
        }

        return hierarchy;
    }

    public List<Hierarchy> getAllHierarchies() {
        return hierarchyRepository.findAll();
    }

    public Branch getBranch(Long id) {
        return branchRepository.findOne(id);
    }

    public Branch getBranch(Hierarchy hierarchy, Locale locale) {
        Assert.notNull(locale);
        Branch branch = hierarchy.getBranch(locale);
        if (branch == null) {
            branch = new Branch();
            branch.setLocale(locale);
            branch.setHierarchy(hierarchy);
            branch = branchRepository.save(branch);
            hierarchy.putBranch(branch);
        }

        return branch;
    }

    public Branch saveBranch(Branch branch) {
        return branchRepository.save(branch);
    }


    public Node getNode(Long id) {
        return nodeRepository.findOne(id);
    }

    public Node getNode(UUID uuid) {
        return nodeRepository.findByUuid(uuid);
    }

    public List<Node> getNodes(Item item, Hierarchy hierarchy) {
        return item.getNodes().stream()
                .filter(it -> it.getBranch().getHierarchy().getName() == hierarchy.getName())
                .collect(Collectors.toList());
    }

    public List<Node> getNodes(Item item, String hierarchyName) {
        return getNodes(item, getHierarchy(hierarchyName));
    }

    public Node getOrCreateNode(Hierarchy hierarchy, Item item) {
        Locale locale = item.getLocale();
        Assert.notNull(locale);
        Branch branch = getBranch(hierarchy, locale);
        List<Node> nodes = item.getNodes();
        Node node = nodes.stream()
                .filter(it -> locale.equals(it.getBranch().getLocale()))
                .findFirst()
                .orElse(null);

        if (node == null) {
            node = new Node();
            node.setBranch(branch);
            node.setItem(item);
            item.addNode(node);
        }

        return node;
    }

    public Node saveNode(Node node) {
        return nodeRepository.save(node);
    }

    public String getPath(Node node) {
        Branch branch = node.getBranch();
        LinkedList<String> path = new LinkedList<>();
        while (node != null) {
            path.addLast(node.getItem().getLabel());
            node = node.getParent();
        }

        path.addLast(branch.getHierarchy().getName());

        return path.stream().collect(Collectors.joining(" / "));
    }

    public List<Node> getNodes(Branch branch) {
        return branch.getNodes();
    }
}
