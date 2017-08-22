package biz.jovido.seed.content;

import biz.jovido.seed.content.model.*;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Attribute getAttribute(Payload payload) {
        Item item = payload.getItem();
        Structure structure = getStructure(item);
        String attributeName = payload.getAttributeName();
        return structure.getAttribute(attributeName);
    }

    private void applyPayloads(Item item) {
        Structure structure = getStructure(item);
        for (String attributeName : structure.getAttributeNames()) {
            Attribute attribute = structure.getAttribute(attributeName);
            Payload payload = item.getPayload(attributeName);
            if (payload == null) {
                payload = attribute.createPayload();
                item.setPayload(attributeName, payload);
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
