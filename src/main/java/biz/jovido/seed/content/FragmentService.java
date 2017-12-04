package biz.jovido.seed.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public PayloadAttribute<?> getAttribute(PayloadSequence sequence) {
        if (sequence != null) {
            Fragment fragment = sequence.getFragment();
            FragmentStructure structure = getStructure(fragment);
            if (structure != null) {
                String attributeName = sequence.getAttributeName();
                return structure.getAttribute(attributeName);
            }
        }

        return null;
    }

    public void addPayload(PayloadSequence sequence) {
        PayloadAttribute<?> attribute = getAttribute(sequence);
        Payload payload = attribute.createPayload();
        sequence.addPayload(payload);
    }

    public void addPayload(Fragment fragment, String attributeName) {
        PayloadSequence sequence = fragment.getSequence(attributeName);
        addPayload(sequence);
    }

    public void applyPayloadSequences(Fragment fragment) {
        FragmentStructure structure = getStructure(fragment);
        for (String attributeName : structure.getAttributeNames()) {
            PayloadAttribute<?> attribute = structure.getAttribute(attributeName);
            PayloadSequence sequence = fragment.getSequence(attributeName);
            if (sequence == null) {
                sequence = new PayloadSequence();
                fragment.setSequence(attributeName, sequence);
            }

            int remaining = attribute.getRequired() - sequence.getPayloads().size();
            while (remaining-- > 0) {
//                Payload payload = attribute.createPayload();
//                sequence.addPayload(payload);
                addPayload(sequence);
            }
        }
    }

    public Fragment createFragment(FragmentStructure structure) {
        Fragment fragment = new Fragment();

        FragmentHistory history = new FragmentHistory();
        history.setCurrent(fragment);
        fragment.setHistory(history);

        fragment.setStructureName(structure.getName());

        applyPayloadSequences(fragment);

        return fragment;
    }

    public PayloadSequence getSequence(Fragment fragment, String attributeName) {
        if (fragment != null) {
            return fragment.getSequence(attributeName);
        }

        return null;
    }

    public List<Payload> getPayloads(Fragment fragment, String attributeName) {
        PayloadSequence sequence = getSequence(fragment, attributeName);
        if (sequence != null) {
            return sequence.getPayloads().stream()
                    .sorted(Comparator.comparingInt(Payload::getOrdinal))
                    .collect(Collectors.toList());
        }

        return null;
    }

    public Payload getPayload(Fragment fragment, String attributeName, int index) {
        List<Payload> payloads = getPayloads(fragment, attributeName);
        if (payloads != null) {
            return payloads.get(index);
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> T getValue(Fragment fragment, String attributeName, int index) {
        ValuePayload<T> payload = (ValuePayload<T>) getPayload(fragment, attributeName, index);
        if (payload != null) {
            return payload.getValue();
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> void setValue(Fragment fragment, String attributeName, int index, T value) {
        List<Payload> payloads = getPayloads(fragment, attributeName);
        ValuePayload<T> payload = (ValuePayload<T>) payloads.get(index);
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
