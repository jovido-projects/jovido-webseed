package biz.jovido.seed.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.Locale;

/**
 * @author Stephan Grundner
 */
@Service
public class ItemService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private StructureService structureService;

    private void applyPayloads(Item item) {
        Bundle bundle = item.getBundle();
        Structure structure = structureService.getStructure(bundle.getStructureName());
        for (String attributeName : structure.getAttributeNames()) {
            Attribute attribute = structure.getAttribute(attributeName);
            Payload payload = item.getPayload(attributeName);
            if (payload == null) {
                payload = attribute.createPayload();
                item.setPayload(attributeName, payload);
            }
        }
    }

    public Item createItem(Structure structure) {
        Item item = new Item();
        item.setLocale(Locale.ROOT);

        Bundle bundle = new Bundle();
        bundle.setStructureName(structure.getName());
        bundle.putItem(item);

        History history = new History();
        item.setHistory(history);

        applyPayloads(item);

        return item;
    }

    public Item createItem(String structureName) {
        Structure structure = structureService.getStructure(structureName);
        return createItem(structure);
    }

    public Item createItem(Bundle bundle, Locale locale) {
        Item localizedItem = new Item();
        localizedItem.setLocale(locale);
        localizedItem.setBundle(bundle);

        History history = new History();
        localizedItem.setHistory(history);

        applyPayloads(localizedItem);

        return localizedItem;
    }

    public Item saveItem(Item item) {
        return entityManager.merge(item);
    }
}
