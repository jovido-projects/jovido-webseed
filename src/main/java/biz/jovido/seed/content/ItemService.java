package biz.jovido.seed.content;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Locale;

/**
 * @author Stephan Grundner
 */
@Service
public class ItemService {

    private final EntityManager entityManager;
    private final StructureService structureService;

//    public Attribute getAttribute(Fragment fragment, String fieldName) {
//        if (fragment != null) {
//            Structure structure = fragment.getStructure();
//            if (structure != null) {
//                return structure.getAttribute(fieldName);
//            }
//        }
//
//        return null;
//    }

    private void applyPayloads(Field field, int n) {
        Fragment fragment = field.getFragment();
        Structure structure = getStructure(fragment);
        Attribute attribute = structure.getAttribute(field.getName());
        int remaining = n - field.getPayloads().size();
        while (remaining-- > 0) {
            field.appendPayload(attribute.createPayload());
        }
    }

    private void applyPayloads(Field field) {
        Fragment fragment = field.getFragment();
        Structure structure = getStructure(fragment);
        Attribute attribute = structure.getAttribute(field.getName());
        applyPayloads(field, attribute.getRequired());
    }

    @SuppressWarnings("unchecked")
    public void applyFields(Fragment fragment) {
        Structure structure = getStructure(fragment);
        for (Attribute attribute : structure.getAttributes()) {
            String fieldName = attribute.getFieldName();
            Field field = fragment.getField(fieldName);
            if (field == null) {
                field = new Field(fieldName);
                fragment.putField(field);
            }

            if (!FragmentAttribute.class.isAssignableFrom(attribute.getClass())) {
                applyPayloads(field);
            }
        }
    }

    public Structure getStructure(Fragment fragment) {
        return structureService.getStructure(fragment.getStructureName());
    }

    public Fragment createFragment(Structure structure) {
        Assert.notNull(structure, "[structure] must not be null");

        Fragment fragment = new Fragment();
        fragment.setStructureName(structure.getName());

        applyFields(fragment);

        return fragment;
    }

    public Fragment createFragment(String structureName) {
        Structure structure = structureService.getStructure(structureName);
        return createFragment(structure);
    }

    @Transactional
    public Fragment saveFragment(Fragment fragment) {
        return entityManager.merge(fragment);
    }

    public Item createItem(Structure structure, Locale locale) {
        Item item = new Item();
        item.setLocale(locale);
        Fragment fragment = createFragment(structure);
        item.setCurrentFragment(fragment);
        return item;
    }

    public Item createItem(String structureName, Locale locale) {
        Structure structure = structureService.getStructure(structureName);
        return createItem(structure, locale);
    }

    @Transactional
    public Item saveItem(Item item) {
        Fragment currentFragment = item.getCurrentFragment();
        currentFragment = saveFragment(currentFragment);
        item.setCurrentFragment(currentFragment);
        Item saved = entityManager.merge(item);

        return saved;
    }

    @Transactional
    public Item findItemById(Long id) {
        Item item = entityManager.find(Item.class, id);

        return item;
    }

    public List<Item> findItems(String filter) {
        TypedQuery<Item> findItemsQuery = entityManager.createQuery("from Item i", Item.class);
        return findItemsQuery.getResultList();
    }

    public Object getValue(Fragment fragment, String fieldName, int index) {
        Field field = fragment.getField(fieldName);
        if (index >= field.size()) {
            return null;
        }

        Payload payload = field.getPayloads().get(index);
        return payload.getValue();
    }

    public void setValue(Fragment fragment, String fieldName, int index, Object value) {
        Structure structure = getStructure(fragment);
        Field field = fragment.getField(fieldName);
        List<Payload> payloads = field.getPayloads();
        if (index >= payloads.size()) {
            int remaining = (index + 1) - payloads.size();
            while (remaining-- > 0) {
                Attribute attribute = structure.getAttribute(fieldName);
                Payload payload = attribute.createPayload();
                field.appendPayload(payload);
            }
        }

        Payload payload = payloads.get(index);
        payload.setValue(value);
    }

    private Fragment duplicateFragment(Fragment source) {
        Structure structure = getStructure(source);
        Fragment copy = createFragment(structure);
        for (Attribute attribute : structure.getAttributes()) {
            String fieldName = attribute.getFieldName();
            for (int i = 0; i < source.getField(fieldName).size(); i++) {
                Object value = getValue(source, fieldName, i);
                if (value instanceof Fragment) {
                    value = duplicateFragment((Fragment) value);
                }
                setValue(copy, fieldName, i, value);
            }
        }

        return copy;
    }

    @Transactional
    public void activateItem(Item item) {
        Fragment current = item.getCurrentFragment();
        Fragment active = duplicateFragment(current);
        active = saveFragment(active);
        item.setActiveFragment(active);
        saveItem(item);
    }

    public ItemService(EntityManager entityManager, StructureService structureService) {
        this.entityManager = entityManager;
        this.structureService = structureService;
    }
}
