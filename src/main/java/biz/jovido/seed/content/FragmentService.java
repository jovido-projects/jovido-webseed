package biz.jovido.seed.content;

import biz.jovido.seed.content.structure.*;
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
    private StructureService structureService;

    public Structure getStructure(Fragment fragment) {
        if (fragment != null) {
            String structureName = fragment.getStructureName();
            return structureService.getStructure(structureName);
        }

        return null;
    }

    public Attribute getAttribute(Fragment fragment, String attributeName) {
        if (fragment != null) {
            Structure structure = getStructure(fragment);
            if (structure != null) {
                return structure.getAttribute(attributeName);
            }
        }

        return null;
    }

    public Attribute getAttribute(PayloadSequence list) {
        if (list != null) {
            Fragment fragment = list.getFragment();
            String attributeName = list.getAttributeName();
            return getAttribute(fragment, attributeName);
        }

        return null;
    }

    public Attribute getAttribute(Payload payload) {
        PayloadSequence sequence = payload.getSequence();
        if (sequence != null) {
            return getAttribute(sequence);
        }

        return null;
    }

    public void applySequences(Fragment fragment) {
        Structure structure = getStructure(fragment);
        for (String attributeName : structure.getAttributeNames()) {

            PayloadSequence sequence = fragment.getSequence(attributeName);
            if (sequence == null) {
                sequence = new PayloadSequence();
                fragment.setSequence(attributeName, sequence);
            }

            Attribute attribute = structure.getAttribute(attributeName);
            int remaining = attribute.getRequired() - sequence.size();
            while (remaining-- > 0) {
                Payload payload = new Payload();
                sequence.addPayload(payload);

//                if (attribute instanceof FragmentAttribute) {
//                    Structure preferredStructure = ((FragmentAttribute) attribute).getPreferredStructure();
//                    if (preferredStructure != null) {
//                        Fragment nestedFragment = createFragment(preferredStructure);
//                        payload.setFragment(nestedFragment);
//                    }
//                }
            }
        }
    }

    public Fragment createFragment(Structure structure) {
        Fragment fragment = new Fragment();

        FragmentHistory history = new FragmentHistory();
        history.setCurrent(fragment);
        fragment.setHistory(history);

        fragment.setStructureName(structure.getName());

        applySequences(fragment);

        return fragment;
    }

    public Fragment createFragment(String structureName) {
        Structure structure = structureService.getStructure(structureName);
        return createFragment(structure);
    }

    public Payload getPayload(Fragment fragment, String attributeName, int index) {
        PayloadSequence sequence = fragment.getSequence(attributeName);
        if (sequence != null) {
            List<Payload> payloads = sequence.getPayloads();
            if (payloads != null) {
                return payloads.get(index);
            }
        }

        return null;
    }

    public Object getValue(Payload payload) {
        PayloadSequence list = payload.getSequence();
        Attribute attribute = getAttribute(list);
        if (attribute instanceof TextAttribute) {
            return payload.getText();
        } else if (attribute instanceof FragmentAttribute) {
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
        PayloadSequence list = payload.getSequence();
        Attribute attribute = getAttribute(list);
        if (attribute instanceof TextAttribute) {
            payload.setText((String) value);
        } else if (attribute instanceof FragmentAttribute) {
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
