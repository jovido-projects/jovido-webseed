package biz.jovido.seed.content;

import java.util.*;

/**
 * @author Stephan Grundner
 */
public class FragmentPayloadAttribute extends PayloadAttribute<FragmentPayload> {

    private final Map<String, FragmentStructure> assignableStructureByName = new LinkedHashMap<>();
    private FragmentStructure preferredStructure;

    public Set<String> getAssignableStructureNames() {
        return Collections.unmodifiableSet(assignableStructureByName.keySet());
    }

    public Collection<FragmentStructure> getAssignableStructures() {
        return Collections.unmodifiableCollection(assignableStructureByName.values());
    }

    public FragmentStructure getAssignableStructure(String name) {
        return assignableStructureByName.get(name);
    }

    public FragmentStructure addAssignableStructure(FragmentStructure structure) {
        return assignableStructureByName.put(structure.getName(), structure);
    }

    public FragmentStructure removeAssignableStructure(String name) {
        return assignableStructureByName.remove(name);
    }

    public FragmentStructure getPreferredStructure() {
        return preferredStructure;
    }

    public void setPreferredStructure(FragmentStructure preferredStructure) {
        this.preferredStructure = preferredStructure;
    }

    @Override
    public FragmentPayload createPayload() {
        return new FragmentPayload();
    }
}
