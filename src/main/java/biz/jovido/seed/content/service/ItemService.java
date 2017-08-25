package biz.jovido.seed.content.service;

import biz.jovido.seed.content.model.*;
import org.springframework.beans.factory.annotation.Autowired;
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
    private EntityManager entityManager;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private StructureService structureService;

    @Autowired
    private AuditingHandler auditingHandler;


//    public Structure getStructure(Bundle bundle) {
//        String name = bundle.getStructureName();
//        return structureService.getStructure(name);
//    }

    public Structure getStructure(Item item) {
        String name = item.getStructureName();
        return structureService.getStructure(name);
    }

    public Structure getStructure(String structureName) {
        return structureService.getStructure(structureName);
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
                Payload payload = attribute.createPayload();
                sequence.addPayload(payload);
            }
        }
    }

    private Item createItem(Bundle bundle, String structureName, Locale locale) {
        Chronicle chronicle = new Chronicle();
        chronicle.setLocale(locale);
        bundle.putChronicle(chronicle);

        Item item = new Item();
        item.setChronicle(chronicle);
        item.setStructureName(structureName);

        applyPayloads(item);

        return item;
    }

    public Item createItem(String structureName, Locale locale) {
        Bundle bundle = new Bundle();
//        bundle.setStructureName(structureName);
        return createItem(bundle, structureName, locale);
    }

    public Item getItem(Long id) {
        return itemRepository.findOne(id);
    }

    public List<Locale> getAllSupportedLocales() {
        return Stream.of("de", "en", "it", "es")
                .map(Locale::forLanguageTag)
                .collect(Collectors.toList());
    }

    @Transactional
    public Item saveItem(Item item) {
        auditingHandler.markModified(item);
        return entityManager.merge(item);
    }

    public Page<Item> findAllItems(int offset, int max) {
//        return itemRepository.findAll(new PageRequest(offset, max));
        return itemRepository.findAllByChronicleIsNotNull(new PageRequest(offset, max));
    }

    public ItemService(StructureService structureService) {
        this.structureService = structureService;
    }
}
