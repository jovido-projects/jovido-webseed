package biz.jovido.seed.content.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
@Entity
public class Type {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToOne
    private Structure active;

//    @OneToMany(mappedBy = "type")
//    private final List<Structure> structures = new ArrayList<>();

    @OneToMany(mappedBy = "type", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @MapKey(name = "revision")
    @OrderBy("revision")
    private final Map<Integer, Structure> structureByRevision = new LinkedHashMap<>();

    public Long getId() {
        return id;
    }

    /*default*/ void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Structure getActive() {
        return active;
    }

    public void setActive(Structure active) {
        this.active = active;
    }

    public Collection<Structure> getStructures() {
        return Collections.unmodifiableCollection(structureByRevision.values());
    }

    public Structure getStructure(int revision) {
        return structureByRevision.get(revision);
    }

    public Structure setStructure(int revision, Structure structure) {
        Structure existing = structureByRevision.put(revision, structure);

        if (existing != null) {
            existing.setType(null);
            existing.setRevision(0);
        }

        if (structure != null) {
            structure.setType(this);
            structure.setRevision(revision);
        }

        return existing;
    }
}
