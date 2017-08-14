package biz.jovido.seed.content;

import biz.jovido.seed.content.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private StructureService structureService;

    public Structure getStructure(Bundle bundle) {
        String name = bundle.getStructureName();
        return structureService.getStructure(name);
    }

    public Structure getStructure(Item item) {
        Chronicle chronicle = item.getChronicle();
        Bundle bundle = chronicle.getBundle();
        return getStructure(bundle);
    }

    private void applyProperties(Item item) {
        Structure structure = getStructure(item);
        for (String attributeName : structure.getAttributeNames()) {
            Attribute attribute = structure.getAttribute(attributeName);
            Property property = item.getProperty(attribute.getName());
            if (property == null) {
                item.setProperty(attribute.getName(),
                        property = new Property());
            }
            List<Payload> values = property.getPayloads();
            int remaining = attribute.getRequired() - values.size();
            while (remaining-- > 0) {
                Payload value = attribute.createValue();
                property.addPayload(value);
            }
        }
    }

    public Item createItem(Bundle bundle, Locale locale) {
        Chronicle chronicle = new Chronicle();
        chronicle.setLocale(locale);
        bundle.putChronicle(chronicle);

        Item item = new Item();
        item.setChronicle(chronicle);

        applyProperties(item);

        return item;
    }

    public Item createItem(String structureName, Locale locale) {
        Bundle bundle = new Bundle();
        bundle.setStructureName(structureName);
        return createItem(bundle, locale);
    }

    public List<Locale> getAllSupportedLocales() {
        return Stream.of("de", "en", "it", "es")
                .map(Locale::forLanguageTag)
                .collect(Collectors.toList());
    }

    public Item saveItem(Item item) {
        return entityManager.merge(item);
    }

    public ItemService(StructureService structureService) {
        this.structureService = structureService;
    }
}
