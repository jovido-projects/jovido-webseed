package biz.jovido.seed.content.service;

import biz.jovido.seed.content.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.auditing.AuditingHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Stephan Grundner
 */
@Service
public class ItemService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private StructureService structureService;

    @Autowired
    private RootRepository rootRepository;

    @Autowired
    private AuditingHandler auditingHandler;

    @Autowired
    private NodeRepository nodeRepository;

    @Deprecated
    public Structure getStructure(Item item) {
        return item.getStructure();
    }

    public Attribute getAttribute(Sequence sequence) {
        Item item = sequence.getItem();
        Structure structure = getStructure(item);
        String attributeName = sequence.getAttributeName();
        return structure.getAttribute(attributeName);
    }

    public Attribute getAttribute(Payload payload) {
        return getAttribute(payload.getSequence());
    }

    private void applyPayloads(Item item) {
        Structure structure = getStructure(item);
        for (String attributeName : structure.getAttributeNames()) {
            Attribute attribute = structure.getAttribute(attributeName);
            Sequence sequence = item.getSequence(attributeName);
            if (sequence == null) {
                sequence = new Sequence();
                item.setSequence(attributeName, sequence);
            }

            int remaining = attribute.getRequired() - sequence.length();
            while (remaining-- > 0) {
                sequence.addPayload();
            }
        }
    }

    public Item getItem(Long id) {
        return itemRepository.findOne(id);
    }

    public Page<Item> findAllItems(int offset, int max) {
//        return itemRepository.findAll(new PageRequest(offset, max));
//        return itemRepository.findAllByChronicleIsNotNull(new PageRequest(offset, max));
        return itemRepository.findAllByBundleIsNotNull(new PageRequest(offset, max));
    }

    private Item createItem(Bundle bundle, int structureRevision) {
        Type type = bundle.getType();
        Structure structure = type.getStructure(structureRevision);

        Item item = new Item();
        item.setBundle(bundle);
        item.setStructure(structure);
        bundle.setDraft(item);

        applyPayloads(item);

        return item;
    }

    @Transactional
    public Item createItem(String typeName, int structureRevision, Locale locale) {
        Type type = structureService.getType(typeName);

        Bundle bundle = new Bundle();
        bundle.setType(type);
        bundle.setLocale(locale);
//        entityManager.persist(bundle);

        return createItem(bundle, structureRevision);
    }

    @Deprecated
    public Item createEmbeddedItem(String typeName, int structureRevision) {
        Type type = structureService.getType(typeName);
        Structure structure = type.getStructure(structureRevision);

        Item item = new Item();
        item.setBundle(null);
        item.setStructure(structure);

        applyPayloads(item);

        return item;
    }

    @Transactional
    public Item saveItem(Item item) {
        auditingHandler.markModified(item);
        return entityManager.merge(item);
    }

    public List<Locale> getAllSupportedLocales() {
        return Stream.of("de", "en", "it", "es")
                .map(Locale::forLanguageTag)
                .collect(Collectors.toList());
    }

    public Node getNode(Long id) {
        return nodeRepository.findOne(id);
    }

    public List<Node> getNodes(Item item, String hierarchyName) {
        if (item != null && item.getId() == null) {
            return Collections.EMPTY_LIST;
        }
//        return item.getNodes().stream()
//                .filter(it -> it != null)
//                .filter(it -> it.getRoot() != null)
//                .filter(it -> hierarchyName.equals(it.getRoot().getHierarchy().getName()))
//                .collect(Collectors.toList());


//        return nodeRepository.findAllByItemAndRoot_Hierarchy_Name(item, hierarchyName);

        return item.getNodes().stream()
                .filter(it -> it.getRoot().getHierarchy().getName().equals(hierarchyName))
                .collect(Collectors.toList());
    }

    public Node saveNode(Node node) {
        return nodeRepository.saveAndFlush(node);
    }

    public Root getRoot(Hierarchy hierarchy, Locale locale) {
        return rootRepository.findByHierarchyAndLocale(hierarchy, locale);
    }

    public Root getRoot(String hierarchyName, Locale locale) {
        Hierarchy hierarchy = structureService.getHierarchy(hierarchyName);
        return rootRepository.findByHierarchyAndLocale(hierarchy, locale);
    }

    public Root getOrCreateRoot(Hierarchy hierarchy, Locale locale) {
        Root root = getRoot(hierarchy, locale);
        if (root == null) {
            root = new Root();
            hierarchy.setRoot(locale, root);
            hierarchy = structureService.saveHierarchy(hierarchy);
        }

        return hierarchy.getRoot(locale);
    }

    public Root getOrCreateRoot(String hierarchyName, Locale locale) {
        Hierarchy hierarchy = structureService.getOrCreateHierarchy(hierarchyName);
        return getOrCreateRoot(hierarchy, locale);
    }

//    @Deprecated
//    public Root getRootNodes(Item item, String hierarchyName, Locale locale) {
//        List<Node> nodes = item.getNodes();
//        Root root = null;
//        for (Node node : nodes) {
//            root = node.getRoot();
//            if (root != null && locale == root.getLocale()) {
//                Hierarchy hierarchy = root.getHierarchy();
//                if (hierarchy != null && hierarchyName.equals(hierarchy.getName())) {
//                    break;
//                }
//            }
//        }
//
//        if (root == null) {
//            root = new Root();
//
//        }
//
//        return root;
//    }

    public ItemService(StructureService structureService) {
        this.structureService = structureService;
    }
}
