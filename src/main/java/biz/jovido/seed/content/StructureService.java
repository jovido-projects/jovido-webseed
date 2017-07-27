package biz.jovido.seed.content;

import biz.jovido.seed.QueryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Stephan Grundner
 */
@Service
public class StructureService {

    @Autowired
    private EntityManager entityManager;

    private Map<String, Integer> activeRevisionByName = new ConcurrentHashMap<>();

    public Structure getStructure(String name, int revision) {
        TypedQuery<Structure> findStructureQuery = entityManager.createQuery(
                "from Structure " +
                        "where name = ? " +
                        "and revision = ?",
                Structure.class);
        findStructureQuery.setParameter(1, name);
        findStructureQuery.setParameter(2, revision);
        return QueryUtils.getSingleResult(findStructureQuery);
    }

    public Structure getStructure(String name) {
        Integer revision = activeRevisionByName.get(name);
        Assert.notNull(revision, "[revision] is null");
        return getStructure(name, revision);
    }

    public void activateStructure(Structure structure) {
        activeRevisionByName.put(
                structure.getName(),
                structure.getRevision());
    }

    public void activateStructure(String name, int revision) {
        Structure structure = getStructure(name, revision);
        activateStructure(structure);
    }

    public StructureConfigurer configure(String name, int revision) {
        Structure structure = getStructure(name, revision);
        if (structure == null) {
            structure = new Structure();
            structure.setName(name);
            structure.setRevision(revision);
        }

        return new StructureConfiguration(structure, this);
    }

    public Structure saveStructure(Structure structure) {
        return entityManager.merge(structure);
    }
}
