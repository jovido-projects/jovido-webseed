package biz.jovido.seed.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@Service
public class FragmentService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private FragmentStructureService structureService;

    public FragmentStructure getStructure(Fragment fragment) {
        if (fragment != null) {
            String structureName = fragment.getStructureName();
            return structureService.getStructure(structureName);
        }

        return null;
    }

    public <T> PayloadAttribute<T> getAttribute(PayloadList<? extends Payload<?>> list) {
        if (list != null) {
            Fragment fragment = list.getFragment();
            FragmentStructure structure = getStructure(fragment);
            if (structure != null) {
                String attributeName = list.getAttributeName();
                return (PayloadAttribute<T>) structure.getAttribute(attributeName);
            }
        }

        return null;
    }

//    public <T, P extends Payload<T>> void addPayload(PayloadList<P> list) {
//        PayloadAttribute<T> attribute = getAttribute(list);
//        P payload = (P) attribute.createPayload();
//        list.add(payload);
//    }
    public void addPayload(PayloadList<Payload<?>> list) {
        PayloadAttribute<?> attribute = getAttribute(list);
        Payload<?> payload = attribute.createPayload();
        list.add(payload);
    }

//    public <T, P extends Payload<T>> void addPayload(Fragment fragment, String attributeName) {
//        PayloadList<P> list = (PayloadList<P>) fragment.getPayloadList(attributeName);
//        addPayload(list);
//    }

    public void applyPayloadLists(Fragment fragment) {
        FragmentStructure structure = getStructure(fragment);
        for (String attributeName : structure.getAttributeNames()) {
            PayloadAttribute<?> attribute = structure.getAttribute(attributeName);
            PayloadList<Payload<?>> list = fragment.getPayloadList(attributeName);
            if (list == null) {
                list = new PayloadList<>();
                fragment.setPayloadList(attributeName, list);
            }

            int remaining = attribute.getRequired() - list.size();
            while (remaining-- > 0) {
                addPayload(list);
            }
        }
    }

    public Fragment createFragment(FragmentStructure structure) {
        Fragment fragment = new Fragment();

        FragmentHistory history = new FragmentHistory();
        history.setCurrent(fragment);
        fragment.setHistory(history);

        fragment.setStructureName(structure.getName());

        applyPayloadLists(fragment);

        return fragment;
    }

    public Fragment createFragment(String structureName) {
        FragmentStructure structure = structureService.getStructure(structureName);
        return createFragment(structure);
    }

    public Payload<?> getPayload(Fragment fragment, String attributeName, int index) {
        List<? extends Payload<?>> list = fragment.getPayloadList(attributeName);
        if (list != null) {
            return list.get(index);
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> T getValue(Fragment fragment, String attributeName, int index) {
        Payload<T> payload = (Payload<T>) getPayload(fragment, attributeName, index);
        if (payload != null) {
            return payload.getValue();
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> void setValue(Fragment fragment, String attributeName, int index, T value) {
        List<? extends Payload<?>> list = fragment.getPayloadList(attributeName);
        Payload<T> payload = (Payload<T>) list.get(index);
        payload.setValue(value);
    }

    public Fragment saveFragment(Fragment fragment) {
        if (fragment.getId() == null) {

            entityManager.persist(fragment);

            return fragment;
        } else {
            return entityManager.merge(fragment);
        }
    }
}
