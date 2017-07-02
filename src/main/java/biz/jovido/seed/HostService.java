package biz.jovido.seed;

import biz.jovido.seed.utils.QueryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@Service
public class HostService {

    @Autowired
    private EntityManager entityManager;

    public Host findHostById(Long id) {
        return entityManager.find(Host.class, id);
    }

    public Host findHostByName(String name) {
        TypedQuery<Host> query = entityManager.createQuery(
                "from Host where name = ?", Host.class);
        query.setParameter(1, name);
        return QueryUtils.getSingleResult(query);
    }

    @Transactional
    public boolean registerHostByName(String name) {
        Host host = findHostByName(name);
        if (host == null) {
            host = new Host();
            host.setName(name);
            entityManager.persist(host);
            return true;
        }

        return false;
    }

    public List<Host> getAllHosts() {
        TypedQuery<Host> query = entityManager.createQuery(
                "from Host", Host.class);
        return query.getResultList();
    }
}
