package biz.jovido.seed.content;

import biz.jovido.seed.AliasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Locale;

/**
 * @author Stephan Grundner
 */
@Service
public class ItemService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AliasService aliasService;

    @Autowired
    private StructureService structureService;

    private <T> void applyPayloads(Property property) {
        Item item = property.getItem();
        Bundle bundle = item.getBundle();
        Structure structure = bundle.getStructure();
        Attribute attribute = structure.getAttribute(property.getName());

        List<Payload> payloads = property.getPayloads();
        int remaining = attribute.getRequired() - payloads.size();
        while (remaining-- > 0) {
            Payload<?> payload = attribute.createPayload();
            property.addPayload(payload);
        }

        for (int i = 0; i < payloads.size(); i++) {
            Payload<?> payload = payloads.get(i);
            payload.setOrdinal(i + 1);
        }
    }

    private void applyProperties(Item item) {
        Bundle bundle = item.getBundle();
        Structure structure = bundle.getStructure();
        for (Attribute attribute : structure.getAttributes()) {
            String name = attribute.getName();
            Property property = item.getProperty(name);
            if (property == null) {
                property = new Property();
                property.setItem(item);
                property.setName(name);
            }

            applyPayloads(property);

            item.putProperty(property);
        }
    }

    public Item createNewItem(Structure structure, Locale locale) {
        Item item = new Item();
        item.setRevision(1);

        Bundle bundle = new Bundle();
        bundle.setStructure(structure);
        bundle.setLocale(locale);
        bundle.setDraft(item);
        item.setBundle(bundle);

        applyProperties(item);

        return item;
    }

    public Item createItemRevision(Bundle bundle, int revision) {
        Item item = new Item();
        item.setRevision(revision);

        bundle.setDraft(item);
        item.setBundle(bundle);

        applyProperties(item);

        return item;
    }

    public Item findItemById(Long id) {
        return entityManager.find(Item.class, id);
    }

    @Transactional
    public Item saveItem(Item item){
        return entityManager.merge(item);
    }

    @Transactional
    public void activateItem(Item item) {
        Bundle bundle = item.getBundle();
        bundle.setPublished(item);
        item = saveItem(item);

        aliasService.createAliasesFor(item);
    }

    public Object appendNewValue(Item item, String propertyName) {
        Property<?> property = item.getProperty(propertyName);
        return property.appendNewValue();
    }

    public void swapValues(Item item, String propertyName, int i, int j) {
        Property property = item.getProperty(propertyName);
        property.swapPayloads(i, j);
    }

    public Object removeValue(Item item, String propertyName, int index) {
        Property property = item.getProperty(propertyName);
        Payload<?> removed = property.removePayload(index);
        if (removed != null) {
            return removed.getValue();
        }

        return null;
    }
}
