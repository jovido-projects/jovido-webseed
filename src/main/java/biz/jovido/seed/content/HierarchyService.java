package biz.jovido.seed.content;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Stephan Grundner
 */
@Service
public class HierarchyService {

    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private HierarchyRepository hierarchyRepository;

    @Autowired
    private NodeRepository nodeRepository;

    @Autowired
    private EntityManager entityManager;

    public Hierarchy getHierarchy(Long id) {
        return hierarchyRepository.findOne(id);
    }

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

    public Node getNode(Long id) {
        return nodeRepository.findOne(id);
    }

    public Node getNode(UUID uuid) {
        return nodeRepository.findByUuid(uuid);
    }

    public List<Node> getNodes(Item item, Hierarchy hierarchy) {
        return item.getLeaf().getNodes().stream()
                .filter(it -> hierarchy.getName().equals(it.getHierarchy().getName()))
                .collect(Collectors.toList());
    }

//    public List<Node> getNodes(Item item, String hierarchyName) {
//        return getNodes(item, getHierarchy(hierarchyName));
//    }

    public List<Node> getRootNodes(Hierarchy hierarchy) {
        return hierarchy.getNodes().stream()
                .filter(it -> it.getParent() == null)
                .collect(Collectors.toList());
    }

    public List<Node> getRoots(Hierarchy hierarchy) {
        return getRootNodes(hierarchy);
    }

    public Node saveNode(Node node) {
        return nodeRepository.save(node);
    }

    public String getPath(Node node) {
        ItemService itemService = beanFactory.getBean(ItemService.class);
        LinkedList<String> path = new LinkedList<>();
        while (node != null) {
            Leaf history = node.getLeaf();
            Item item = history.getCurrent();
//            String labelText = ItemUtils.labelToString(item);
            String labelText = itemService.getLabel(item).toString();
            path.addLast(labelText);
            node = node.getParent();
        }

        path.addLast(node.getHierarchy().getName());

        return path.stream().collect(Collectors.joining(" / "));
    }
}
