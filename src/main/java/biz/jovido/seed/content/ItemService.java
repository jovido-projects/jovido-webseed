package biz.jovido.seed.content;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.auditing.AuditingHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
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

    public Page<Item> findAllItems(int offset, int max) {
        return itemRepository.findAllByLeafIsNotNull(new PageRequest(offset, max));
    }

    public List<Item> findAllItems() {
        return itemRepository.findAllCurrent();
    }

    public List<Item> findAllPublishedItemByPath(String path) {
        return itemRepository.findAllPublishedByPath(path);
    }

    public Structure getStructure(Item item) {
        if (item != null) {
            String structureName = item.getStructureName();
            return structureService.getStructure(structureName);
        }

        return null;
    }

    public Sequence<String> getLabel(Item item) {
        if (item != null) {
            Structure structure = getStructure(item);
            String attributeName = structure.getLabelAttributeName();
            return (Sequence<String>) item.getSequence(attributeName);
        }

        return null;
    }

    public String getLabelText(Item item) {
        if (item != null) {
            Sequence<String> label = getLabel(item);
            if (label == null) {
                Structure structure = getStructure(item);
                Sequence<?> title = item.getSequence("title");
                if (title != null && title.size() > 0) {
                    Object titleObject = title.get(0);
                    return titleObject != null
                            ? titleObject.toString() : null;
                }
                return null;
            }
//        return label != null ? label.get(0) : null;
            return label.get(0);
        }

        return null;
    }

    private Sequence applySequence(Item item, Structure structure, String attributeName) {
        Sequence sequence = item.getSequence(attributeName);
        if (sequence == null) {
            sequence = new Sequence();
            item.setSequence(attributeName, sequence);
        }

        Attribute attribute = structure.getAttribute(attributeName);
        if (!(attribute instanceof ItemAttribute)) {
            int remaining = attribute.getRequired() - sequence.length();
            while (remaining-- > 0) {
                Payload payload = attribute.createPayload();
                sequence.addPayload(payload);
            }
        }

        return sequence;
    }

    private Sequence applySequence(Item item, String attributeName) {
        Structure structure = getStructure(item);
        return applySequence(item, structure, attributeName);
    }

    private void applyPayloads(Item item) {
        Structure structure = getStructure(item);
        for (String attributeName : structure.getAttributeNames()) {
            Sequence sequence = applySequence(item, structure, attributeName);

            Attribute attribute = structure.getAttribute(attributeName);
            if (!(attribute instanceof ItemAttribute)) {
                int remaining = attribute.getRequired() - sequence.length();
                while (remaining-- > 0) {
                    Payload payload = attribute.createPayload();
                    sequence.addPayload(payload);
                }
            }
        }
    }

    /**
     * Get or create a sequence for the specified item and attribute name.
     *
     * @param item
     * @param attributeName
     * @return
     */
    public Sequence getSequence(Item item, String attributeName) {
        Sequence sequence = item.getSequence(attributeName);
        if (sequence == null) {
            sequence = applySequence(item, attributeName);
        }

        return sequence;
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

    public String getPath(Item item) {
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

    public String getHtml(Sequence<String> sequence, int index) {
        int size = sequence.size();
        if (index < size) {
            String html = sequence.get(index);
            return htmlService.filterHtml(html);
        }

        return null;
    }

    public String getHtml(Sequence<String> sequence) {
        return getHtml(sequence, 0);
    }

    public String getHtml(Item item, String attributeName, int index) {
        if (item != null) {
            Sequence<String> sequence = item.getSequence(attributeName);
            if (sequence != null) {
                return getHtml(sequence, index);
            }
        }

        return null;
    }

    public String getHtml(Item item, String attributeName) {
        return getHtml(item, attributeName, 0);
    }

    @Transactional
    public Item saveItem(Item item) {
        if (item.getId() == null) {
            auditingHandler.markCreated(item);
        }
        auditingHandler.markModified(item);
//        return itemRepository.saveAndFlush(item);
//        return itemRepository.saveAndFlush(item);
        return entityManager.merge(item);
    }

    @SuppressWarnings("unchecked")
    private <T> Payload<T> copyPayload(Payload<T> from) {
        Attribute attribute = getAttribute(from);
        Payload<T> to = (Payload<T>) attribute.createPayload();
        to.setValue(from.getValue());

        return to;
    }

    private <T> Sequence<T> copySequence(Sequence<T> from) {
        Sequence<T> to = new Sequence<>();
        to.setAttributeName(from.getAttributeName());

        for (int i = 0; i < from.length(); i++) {
            Payload<T> payload = from.getPayload(i);
            to.addPayload(copyPayload(payload));
        }

        return to;
    }

    private Item copyItem(Item from) {
        Item to = new Item();
        to.setLeaf(from.getLeaf());
        to.setStructureName(from.getStructureName());
        to.setLocale(from.getLocale());
        to.setPath(from.getPath());

        Structure structure = getStructure(from);
        for (String attributeName : structure.getAttributeNames()) {
            Sequence<?> sequence = from.getSequence(attributeName);
            to.setSequence(attributeName, copySequence(sequence));
        }

        return to;
    }

    @Transactional
    public Item publishItem(final Item item) {
        Item current = copyItem(item);
//        auditingHandler.markCreated(item);
        current = entityManager.merge(current);

        Leaf history = current.getLeaf();
//        entityManager.refresh(history);

        history.setPublished(item);
        history.setCurrent(current);

        entityManager.merge(history);

        return current;
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

    public List<Locale> getAllSupportedLocales() {
        return Stream.of("de", "en", "it", "es")
                .map(Locale::forLanguageTag)
                .collect(Collectors.toList());
    }
}
