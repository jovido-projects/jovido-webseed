package biz.jovido.seed.content;

import biz.jovido.seed.utils.QueryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * @author Stephan Grundner
 */
@Service
public class StructureService {

    @Autowired
    private EntityManager entityManager;

    private int activeRevision;

    public int getActiveRevision() {
        return activeRevision;
    }

    public void setActiveRevision(int activeRevision) {
        this.activeRevision = activeRevision;
    }

    public Structure findStructureById(Long id) {
        return entityManager.find(Structure.class, id);
    }

    public Structure findStructureByName(String name, int revision) {
        TypedQuery<Structure> query = entityManager.createQuery(
                "from Structure where name = ? and revision = ?", Structure.class);
        query.setParameter(1, name);
        query.setParameter(2, revision);
        return QueryUtils.getSingleResult(query);
    }

    public Structure findActiveStructureByName(String name) {
        return findStructureByName(name, getActiveRevision());
    }

    @Transactional
    public Structure saveStructure(Structure structure) {
        return entityManager.merge(structure);
    }
}
