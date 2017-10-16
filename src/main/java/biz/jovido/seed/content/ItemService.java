package biz.jovido.seed.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
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
    private TypeService typeService;

    @Autowired
    private EntityManager entityManager;

    public Item getItem(Long id) {
        return itemRepository.findOne(id);
    }
    
    public Page<Item> findAllItems(int offset, int max) {
//        return itemRepository.findAll(new PageRequest(offset, max));
//        return itemRepository.findAllByChronicleIsNotNull(new PageRequest(offset, max));
        return itemRepository.findAllByChronicleIsNotNull(new PageRequest(offset, max));
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
                sequence.addPayload();
            }
        }
    }

    private Item createItem(Type type, int revision) {
        Structure structure = type.getStructure(revision);
        return createItem(structure);
    }

    private Item createItem(String typeName, int revision) {
        Type type = typeService.getType(typeName);
        return createItem(type, revision);
    }

    public Item createItem(String typeName) {
        int revision = typeService.getActiveRevision(typeName);
        return createItem(typeName, revision);
    }

    private Item createItem(Structure structure, Leaf chronicle) {
        Item item = new Item();
        item.setLeaf(chronicle);
        if (chronicle != null) {
            chronicle.setDraft(item);
        }

        item.setStructure(structure);

        applyPayloads(item);

        return item;
    }

    private Item createItem(Structure structure) {
        Leaf chronicle = new Leaf();
        chronicle.setLocale(LocaleContextHolder.getLocale());
        return createItem(structure, chronicle);
    }

    public Item createEmbeddedItem(String typeName, int revision) {
        Type type = typeService.getType(typeName);
        Structure structure = type.getStructure(revision);
        return createItem(structure, null);
    }

    @Transactional
    public Item saveItem(Item item) {
//        auditingHandler.markModified(item);
        return itemRepository.saveAndFlush(item);
//        entityManager.merge(item);
//        return item;
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
