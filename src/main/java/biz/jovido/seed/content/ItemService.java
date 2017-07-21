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

    public void applyElements(Property property) {
        Item item = property.getItem();
        Structure structure = structureService.getStructure(
                item.getStructureName());

        String propertyName = property.getName();
        Field field = structure.getField(propertyName);

        int remaining = field.getRequired() - property.getElements().size();
        while (remaining-- > 0) {
            Element element = property.appendElement();
        }
    }

    public void applyProperties(Item item) {
        Structure structure = structureService.getStructure(
                item.getStructureName());

        for (String propertyName : structure.getPropertyNames()) {
            Field field = structure.getField(propertyName);
            Property property = item.getProperty(propertyName);
            if (property == null) {
                property = new Property();
                item.setProperty(propertyName, property);
            }
            applyElements(property);
        }
    }

    public Item createItem(Structure structure) {
        Item item = new Item();
        item.setStructureName(structure.getName());

        applyProperties(item);

        return item;
    }

    private Payload getOrCreatePayload(Item item, String propertyName, int index, Locale locale) {
        Property property = item.getProperty(propertyName);
        Payload payload = property.getPayload(index, locale);
        if (payload == null) {
            Structure structure = structureService.getStructure(
                    item.getStructureName());
            Field field = structure.getField(propertyName);
            payload = field.createPayload();
            payload.setLocale(locale);
            property.setPayload(index, payload);
        }

        return payload;
    }

    public Object getValue(Item item, String propertyName, int index, Locale locale) {
        Payload payload = getOrCreatePayload(item, propertyName, index, locale);
        return payload.getValue();
    }

    public void setValue(Item item, String propertyName, int index, Locale locale, Object value) {
        Payload payload = getOrCreatePayload(item, propertyName, index, locale);
        payload.setValue(value);
    }

    public void saveItem(Item item) {
        entityManager.merge(item);
    }
}
