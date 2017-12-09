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

    public PayloadAttribute getAttribute(Fragment fragment, String attributeName) {
        if (fragment != null) {
            FragmentStructure structure = getStructure(fragment);
            if (structure != null) {
                return structure.getAttribute(attributeName);
            }
        }

        return null;
    }

    public PayloadAttribute getAttribute(PayloadList list) {
        if (list != null) {
            Fragment fragment = list.getFragment();
            String attributeName = list.getAttributeName();
            return getAttribute(fragment, attributeName);
        }

        return null;
    }

    public void applyPayloadLists(Fragment fragment) {
        FragmentStructure structure = getStructure(fragment);
        for (String attributeName : structure.getAttributeNames()) {
            PayloadAttribute attribute = structure.getAttribute(attributeName);
            PayloadList list = fragment.getPayloadList(attributeName);
            if (list == null) {
                list = new PayloadList();
                fragment.setPayloadList(attributeName, list);
            }

            int remaining = attribute.getRequired() - list.size();
            while (remaining-- > 0) {
                list.add(new Payload());
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

    public Payload getPayload(Fragment fragment, String attributeName, int index) {
        List<Payload> list = fragment.getPayloadList(attributeName);
        if (list != null) {
            return list.get(index);
        }

        return null;
    }

    public Object getValue(Payload payload) {
        PayloadList list = payload.getList();
        PayloadAttribute attribute = getAttribute(list);
        if (attribute instanceof TextPayloadAttribute) {
            return payload.getText();
        } else if (attribute instanceof FragmentPayloadAttribute) {
            return payload.getFragment();
        } else {
            throw new RuntimeException(String.format("Unexpected attribute type [%s]",
                    attribute.getClass()));
        }
    }

    public Object getValue(Fragment fragment, String attributeName, int index) {
        Payload payload = getPayload(fragment, attributeName, index);
        return getValue(payload);
    }

    public void setValue(Payload payload, Object value) {
        PayloadList list = payload.getList();
        PayloadAttribute attribute = getAttribute(list);
        if (attribute instanceof TextPayloadAttribute) {
            payload.setText((String) value);
        } else if (attribute instanceof FragmentPayloadAttribute) {
            payload.setFragment((Fragment) value);
        } else {
            throw new RuntimeException(String.format("Unexpected attribute type [%s]",
                    attribute.getClass()));
        }
    }

    public void setValue(Fragment fragment, String attributeName, int index, Object value) {
        Payload payload = getPayload(fragment, attributeName, index);
        setValue(payload, value);
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
