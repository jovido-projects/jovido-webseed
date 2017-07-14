package biz.jovido.seed.content;

import biz.jovido.seed.QueryUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Stephan Grundner
 */
@Service
public class StructureService {

    private final EntityManager entityManager;

    private Map<String, Integer> activeRevisions = new ConcurrentHashMap<>();

    public int getActiveStructureRevision(String name) {
        Integer revision = activeRevisions.get(name);
        return revision == null ? 0 : revision;
    }

    public void setActiveStructureRevision(String name, int revision) {
        activeRevisions.put(name, revision);
    }

    public Structure getStructure(String name, int revision) {
        TypedQuery<Structure> query = entityManager.createQuery("from Structure where name = ? and revision = ?",
                Structure.class);
        query.setParameter(1, name);
        query.setParameter(2, revision);
        return QueryUtils.getSingleResult(query);
    }

    public Structure getActiveStructure(String name) {
        int revision = getActiveStructureRevision(name);
        return getStructure(name, revision);
    }

    public void setActiveStructure(Structure structure) {
        int revision = structure.getRevision();
        String name = structure.getName();
        setActiveStructureRevision(name, revision);
    }

    public Set<String> getAllStructureNames() {
        return activeRevisions.keySet();
    }

    @Transactional
    public StructureConfigurer getStructureConfiguration(String name, int revision) {
        Structure structure = getStructure(name, revision);
        if (structure == null) {
            structure = new Structure();
            structure.setName(name);
            structure.setRevision(revision);
        }

        return new StructureBuilder(structure);
    }

    @Transactional
    public Structure saveStructure(Structure structure) {
        return entityManager.merge(structure);
    }

    public StructureService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
