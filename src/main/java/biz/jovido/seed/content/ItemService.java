package biz.jovido.seed.content;

import biz.jovido.seed.QueryUtils;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
        Attribute attribute = field.getAttribute();
        int remaining = n - field.getPayloads().size();
        while (remaining-- > 0) {
            field.appendPayload(attribute.createPayload());
        }
    }

    private void applyPayloads(Field field) {
        Attribute attribute = field.getAttribute();
        applyPayloads(field, attribute.getRequired());
    }

    @SuppressWarnings("unchecked")
    public void applyFields(Fragment fragment) {
        Structure structure = fragment.getStructure();
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

    public Fragment createFragment(Structure structure) {
        Assert.notNull(structure, "[structure] must not be null");

        Fragment fragment = new Fragment();
        fragment.setStructure(structure);
//        fragment.setUuid(UUID.randomUUID());

        applyFields(fragment);

        return fragment;
    }

    public Fragment createFragment(String structureName, int revision) {
        Structure structure = structureService.getStructure(structureName, revision);
        return createFragment(structure);
    }

    public Fragment createFragment(String structureName) {
        int revision = structureService.getActiveStructureRevision(structureName);
        return createFragment(structureName, revision);
    }

//    @SuppressWarnings("unchecked")
    @Transactional
    public Fragment saveFragment(Fragment fragment) {
        if (fragment == null || fragment.getStructure() == null) {
            return null;
        }

//        Structure structure = fragment.getStructure();
//        for (Attribute attribute : structure.getAttributes()) {
//            if (attribute instanceof FragmentAttribute) {
//                Field<Fragment> field = fragment.getField(attribute.getFieldName());
//                List<Payload<Fragment>> payloads = field.getPayloads();
//                for (Payload<Fragment> payload : payloads) {
//                    Fragment referredFragment = payload.getValue();
//                    referredFragment.
//                }
//            }
//        }

        return entityManager.merge(fragment);
    }

    public Fragment loadFragmentById(Long id) {
        EntityGraph<?> graph = entityManager.getEntityGraph("fragment.full");
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.fetchgraph", graph);
//        return entityManager.find(Fragment.class, id, properties);
        TypedQuery<Fragment> query = entityManager.createQuery("from Fragment where id = ?", Fragment.class);
        query.setParameter(1, id);
        query.setHint("javax.persistence.fetchgraph", graph);
        return QueryUtils.getSingleResult(query);
    }

    public Item createItem(Structure structure, Locale locale) {
        Item item = new Item();
        item.setLocale(locale);
        Fragment fragment = createFragment(structure);
        item.setCurrentFragment(fragment);
        return item;
    }

    public Item createItem(String structureName, Locale locale) {
        Structure structure = structureService.getActiveStructure(structureName);
        return createItem(structure, locale);
    }

    @Transactional
    public Item saveItem(Item item) {
        Fragment currentFragment = item.getCurrentFragment();
        currentFragment = saveFragment(currentFragment);
        item.setCurrentFragment(currentFragment);
        return entityManager.merge(item);
    }

    @Transactional
    public Item findItemById(Long id) {
        Item item = entityManager.find(Item.class, id);
        entityManager.refresh(item);
        return item;
    }

    public List<Item> findItems(String filter) {
        TypedQuery<Item> findItemsQuery = entityManager.createQuery("from Item i", Item.class);
        return findItemsQuery.getResultList();
    }

    private Fragment duplicateFragment(Fragment source) {
        Structure structure = source.getStructure();
        Fragment copy = createFragment(structure);
        for (Attribute attribute : structure.getAttributes()) {
            String fieldName = attribute.getFieldName();
            for (int i = 0; i < source.getField(fieldName).size(); i++) {
                Object value = source.getValue(fieldName, i);
                if (value instanceof Fragment) {
                    value = duplicateFragment((Fragment) value);
                }
                copy.setValue(fieldName, i, value);
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
