package biz.jovido.seed.content;

import biz.jovido.seed.UsedInTemplates;
import biz.jovido.seed.net.HostService;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.auditing.AuditingHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Stephan Grundner
 */
@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private StructureService structureService;

    @Autowired
    private HierarchyService hierarchyService;

    @Autowired
    private HostService hostService;

    @Autowired
    private HtmlService htmlService;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AuditingHandler auditingHandler;

    public Item getItem(Long id) {
        return itemRepository.findOne(id);
    }

    public Item findPublished(Long leafId) {
        return itemRepository.findPublished(leafId);
    }

    public List<Item> findAllItems() {
        return itemRepository.findAllCurrent();
    }

    public List<Item> findAllPublishedItemsByPath(String path) {
        return itemRepository.findAllPublishedByPath(path);
    }

    public Structure getStructure(Item item) {
        if (item != null) {
            String structureName = item.getStructureName();
            return structureService.getStructure(structureName);
        }

        return null;
    }

    @Deprecated
    public String getLabelText(Item item) {
//        if (item != null) {
//            PayloadGroup label = getLabel(item);
//            if (label == null) {
////                Structure structure = getStructure(item);
//                PayloadGroup title = item.getPayloadGroup("title");
//                if (title != null && title.getPayloads().size() > 0) {
//                    return title.getPayload(0).getText();
//                }
//
//                return null;
//            }
//
//            return (String) label.getPayload(0).getText();
//        }

        return null;
    }

    public List<Payload> getPayloads(Item item, String attributeName) {
        return item.getPayloads().stream()
                .filter(it -> it.getAttributeName().equals(attributeName))
                .sorted(Comparator.comparingInt(Payload::getOrdinal))
                .collect(Collectors.toList());
    }

    public Payload getPayload(Item item, String attributeName, int index) {
        return item.getPayloads().stream()
                .filter(it -> it.getAttributeName().equals(attributeName) && it.getOrdinal() == index)
                .findFirst().orElse(null);
    }

    private void applyPayloads(Item item) {
        Structure structure = getStructure(item);
        for (String attributeName : structure.getAttributeNames()) {
            List<Payload> payloads = getPayloads(item, attributeName);

            Attribute attribute = structure.getAttribute(attributeName);
            if (!(attribute instanceof ItemAttribute)) {
                int remaining = attribute.getRequired() - payloads.size();
                while (remaining-- > 0) {
                    Payload payload = attribute.createPayload();
                    payload.setAttributeName(attributeName);
                    item.addPayload(payload);
                }
            }
        }
    }

    private Item createItem(Structure structure) {
        Leaf leaf = new Leaf();
        return createItem(structure, leaf);
    }

    public Item createItem(String structureName) {
        Structure structure = structureService.getStructure(structureName);
        return createItem(structure);
    }

    private Item createItem(Structure structure, Leaf leaf) {
        Item item = new Item();
        item.setLeaf(leaf);
        item.setStructureName(structure.getName());
        item.setLocale(LocaleContextHolder.getLocale());
        if (leaf != null) {
            leaf.setCurrent(item);
        }

        applyPayloads(item);

        return item;
    }

    public Item createEmbeddedItem(String structureName) {
        Structure structure = structureService.getStructure(structureName);
        return createItem(structure, null);
    }

    public boolean isPublished(Item item) {
        if (item != null) {
            Leaf leaf = item.getLeaf();
            if (leaf != null) {
                return new EqualsBuilder()
                        .append(leaf.getPublished(), item)
                        .isEquals();
            }
        }

        return false;
    }

    public Mode getMode(Item item) {
        return isPublished(item) ? Mode.LIVE : Mode.PREVIEW;
    }

    @UsedInTemplates
    public String getPath(Object object) {
        Item item = (Item) object;
        if (item != null) {
            String path = item.getPath();
            if (StringUtils.isEmpty(path)) {
                Leaf leaf = item.getLeaf();
                return String.format("/item?leaf=%s", leaf.getId());
            }

            if (!path.startsWith("/")) {
                return String.format("/%s", path);
            }

            return path;
        }

        return null;
    }

    @Transactional
    public Item saveItem(Item item) {
        if (item.getId() == null) {
            auditingHandler.markCreated(item);
        }
        auditingHandler.markModified(item);
        return entityManager.merge(item);
    }

    @Transactional
    public Item publishItem(final Item item) {
        Item current = item.copy();
        current = entityManager.merge(current);
        Leaf history = current.getLeaf();
        history.setPublished(item);
        history.setCurrent(current);
        entityManager.merge(history);

        return current;
    }

    public Attribute getAttribute(Item item, String attributeName) {
        Structure structure = getStructure(item);
        return structure.getAttribute(attributeName);
    }

    public Attribute getAttribute(Payload payload) {
        Item item = payload.getItem();
        String attributeName = payload.getAttributeName();
        return getAttribute(item, attributeName);
    }

    public List<Locale> getAllSupportedLocales() {
        return Stream.of("de", "en", "it", "es")
                .map(Locale::forLanguageTag)
                .collect(Collectors.toList());
    }
}
