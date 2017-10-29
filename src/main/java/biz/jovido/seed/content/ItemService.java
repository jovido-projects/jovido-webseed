package biz.jovido.seed.content;

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
    private AliasService aliasService;

    @Autowired
    private HostService hostService;

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

    public Structure getStructure(Item item) {
        if (item != null) {
            String structureName = item.getStructureName();
            return structureService.getStructure(structureName);
        }

        return null;
    }

    public Sequence<?> getLabel(Item item) {
        Structure structure = getStructure(item);
        String attributeName = structure.getLabelAttributeName();
        return item.getSequence(attributeName);
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

            if (!(attribute instanceof ItemAttribute)) {
                int remaining = attribute.getRequired() - sequence.length();
                while (remaining-- > 0) {
                    Payload payload = attribute.createPayload();
                    sequence.addPayload(payload);
                }
            }
        }
    }

    public Sequence getSequence(Item item, String attributeName) {
        Sequence sequence = item.getSequence(attributeName);
        if (sequence == null) {
            throw new RuntimeException("No sequence found for: " + attributeName);
        }

        return sequence;
    }

    private Item createItem(Structure structure) {
        Leaf leaf = new Leaf();
        leaf.setLocale(LocaleContextHolder.getLocale());
        return createItemWithinHistory(structure, leaf);
    }

    public Item createItem(String structureName) {
        Structure structure = structureService.getStructure(structureName);
        return createItem(structure);
    }

    private Item createItemWithinHistory(Structure structure, Leaf leaf) {
        Item item = new Item();
        item.setLeaf(leaf);
        item.setStructureName(structure.getName());
        if (leaf != null) {
            leaf.setCurrent(item);
        }

        applyPayloads(item);

        return item;
    }

    public Item createEmbeddedItem(String structureName) {
        Structure structure = structureService.getStructure(structureName);
        return createItemWithinHistory(structure, null);
    }

    public String getRelativeUrl(Item item) {
        String path = item.getPath();
        if (StringUtils.isEmpty(path)) {
            Leaf history = item.getLeaf();
            return String.format("/item?leaf=%s", history.getId());
        }

        if (!path.startsWith("/")) {
            return String.format("/%s", path);
        }

        return path;
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

        String path = item.getPath();
        if (StringUtils.hasLength(path)) {
            for (Host host : hostService.getAllHosts()) {
                Alias alias = aliasService.getOrCreateAlias(host, path);
                alias.setHistory(history);
                aliasService.saveAlias(alias);
            }
        }

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
