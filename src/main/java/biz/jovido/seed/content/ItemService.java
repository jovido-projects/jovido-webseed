package biz.jovido.seed.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.auditing.AuditingHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private EntityManager entityManager;

    @Autowired
    private AuditingHandler auditingHandler;

    public Item getItem(Long id) {
        return itemRepository.findOne(id);
    }

    public Item findPublished(Long historyId) {
        return itemRepository.findPublished(historyId);
    }

    public Page<Item> findAllItems(int offset, int max) {
        return itemRepository.findAllByHistoryIsNotNull(new PageRequest(offset, max));
    }

    public List<Item> findAllItems() {
        return itemRepository.findAllCurrent();
    }

    private void applyPayloads(Item item) {
        Structure structure = item.getStructure();
        for (String attributeName : structure.getAttributeNames()) {
            Attribute attribute = structure.getAttribute(attributeName);
            Sequence sequence = item.getSequence(attributeName);
            if (sequence == null) {
                sequence = new Sequence();
                item.setSequence(attributeName, sequence);
            }

            int remaining = attribute.getRequired() - sequence.length();
            while (remaining-- > 0) {
                Payload payload = attribute.createPayload();
                sequence.addPayload(payload);
            }
        }
    }

    private Item createItem(Structure structure) {
        History history = new History();
        history.setLocale(LocaleContextHolder.getLocale());
        return createItemWithinHistory(structure, history);
    }

    public Item createItem(String structureName, int structureRevision) {
        Structure structure = structureService.getStructure(structureName, structureRevision);
        return createItem(structure);
    }

    public Item createItem(String structureName) {
        Structure structure = structureService.getStructure(structureName);
        return createItem(structure);
    }

    private Item createItemWithinHistory(Structure structure, History history) {
        Item item = new Item();
        item.setHistory(history);
        if (history != null) {
            history.setCurrent(item);
        }

        item.setStructure(structure);

        applyPayloads(item);

        return item;
    }

    public Item createEmbeddedItem(String structureName, int structureRevision) {
        Structure structure = structureService.getStructure(structureName, structureRevision);
        return createItemWithinHistory(structure, null);
    }

    @Transactional
    public Item saveItem(Item item) {
        if (item.getId() == null) {
            auditingHandler.markCreated(item);
        }
        auditingHandler.markModified(item);
//        return itemRepository.saveAndFlush(item);
        return itemRepository.saveAndFlush(item);
    }

    @Transactional
    public Item publishItem(final Item item) {
        Item current = item.copy();
//        auditingHandler.markCreated(item);
        current = entityManager.merge(current);

        History history = current.getHistory();
//        entityManager.refresh(history);

        history.setPublished(item);
        history.setCurrent(current);

        entityManager.merge(history);

        return current;
    }

    public Attribute getAttribute(Sequence sequence) {
        Item item = sequence.getItem();
        Structure structure = item.getStructure();
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
