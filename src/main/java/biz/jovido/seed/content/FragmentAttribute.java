package biz.jovido.seed.content;

import java.util.*;

/**
 * @author Stephan Grundner
 */
public class FragmentAttribute extends Attribute {

    private final Map<String, Structure> assignableStructureByName = new LinkedHashMap<>();
    private Structure preferredStructure;

    public Set<String> getAssignableStructureNames() {
        return Collections.unmodifiableSet(assignableStructureByName.keySet());
    }

    public Collection<Structure> getAssignableStructures() {
        return Collections.unmodifiableCollection(assignableStructureByName.values());
    }

    public Structure getAssignableStructure(String name) {
        return assignableStructureByName.get(name);
    }

    public Structure addAssignableStructure(Structure structure) {
        return assignableStructureByName.put(structure.getName(), structure);
    }

    public Structure removeAssignableStructure(String name) {
        return assignableStructureByName.remove(name);
    }

    public Structure getPreferredStructure() {
        return preferredStructure;
    }

    public void setPreferredStructure(Structure preferredStructure) {
        this.preferredStructure = preferredStructure;
    }
}
