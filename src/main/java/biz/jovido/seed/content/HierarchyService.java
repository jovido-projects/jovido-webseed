package biz.jovido.seed.content;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.*;
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

//    public List<Node> getNodes(Item item, Hierarchy hierarchy) {
//        return item.getLeaf().getNodes().stream()
//                .filter(it -> hierarchy.getName().equals(it.getHierarchy().getName()))
//                .collect(Collectors.toList());
//    }

    private void deleteNode(Hierarchy hierarchy, Node node) {
        Node parent = node.getParent();
        if (parent != null) {
            parent.removeChild(node);
        }

        List<Node> children = new ArrayList<>(node.getChildren());
        for (Node child : children) {
            deleteNode(hierarchy, child);
        }

        nodeRepository.delete(node);

        /*
         * Wichtig: Muss nach NodeRepository#delete() passieren!
         */
        if (hierarchy != null) {
            hierarchy.removeNode(node);
        }

        ItemHistory itemHistory = node.getItemHistory();
        if (itemHistory != null) {
            itemHistory.removeNode(node);
        }
    }

    public void deleteNode(Node node) {
        Hierarchy hierarchy = node.getHierarchy();
        deleteNode(hierarchy, node);
        saveHierarchy(hierarchy);
    }

    public Node saveNode(Node node) {
        return nodeRepository.saveAndFlush(node);
    }

    public void swapNodes(Node a, Node b) {
        List<Node> nodes = a.getNodesAtSameLevel();
//        TODO Check if both nodes are at the same level
        if (!nodes.contains(b)) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < nodes.size(); i++) {
            Node n = nodes.get(i);
            n.setOrdinal(i);
        }
        int tmp = a.getOrdinal();
        a.setOrdinal(b.getOrdinal());
        b.setOrdinal(tmp);
    }

//    TODO Umbenennen, weil man das mit URL/URI Pfaden verwechseln könnte
    public String getPath(Node node) {
        if (node != null) {
            Hierarchy hierarchy = node.getHierarchy();
            ItemService itemService = beanFactory.getBean(ItemService.class);
            LinkedList<String> path = new LinkedList<>();
            while (node != null) {
                ItemHistory itemHistory = node.getItemHistory();
                Item item = itemHistory.getCurrent();
//            String labelText = ItemUtils.labelToString(item);
                String labelText = itemService.getLabelText(item);
                path.addFirst(labelText);
                node = node.getParent();
            }

//            path.addFirst(hierarchy.getName());

            return path.stream().collect(Collectors.joining(" / "));
        }

        return null;
    }

    public String getLabelText(Node node) {
        if (node != null) {
            ItemService itemService = beanFactory.getBean(ItemService.class);
            Item item = getItem(node, Mode.PREVIEW);
            return itemService.getLabelText(item);
        }

        return null;
    }

    public Item getItem(ItemHistory itemHistory, Mode mode) {
        if (itemHistory != null) {
            if (mode == Mode.PREVIEW) {
                return itemHistory.getCurrent();
            } else {
                return itemHistory.getPublished();
            }
        }

        return null;
    }

    public Item getItem(Node node, Mode mode) {
        if (node != null) {
            ItemHistory itemHistory = node.getItemHistory();
            return getItem(itemHistory, mode);
        }
        return null;
    }
}
