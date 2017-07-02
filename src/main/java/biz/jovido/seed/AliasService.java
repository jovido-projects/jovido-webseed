package biz.jovido.seed;

import biz.jovido.seed.content.Item;
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
public class AliasService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private HostService hostService;

    public Alias findAliasById(Long id) {
        return entityManager.find(Alias.class, id);
    }

    public Alias findAliasByPath(String path, Host host) {
        TypedQuery<Alias> query = entityManager.createQuery(
                "from Alias where path = ? and host = ?", Alias.class);
        query.setParameter(1, path);
        query.setParameter(2, host);
        return QueryUtils.getSingleResult(query);
    }

    public Alias findAliasByPath(String path, String hostName) {
        Host host = hostService.findHostByName(hostName);
        return findAliasByPath(path, host);
    }

    public List<Alias> findAliasesByPath(String path) {
        TypedQuery<Alias> query = entityManager.createQuery(
                "from Alias where path = ?", Alias.class);
        query.setParameter(1, path);
        return query.getResultList();
    }

    private Alias saveAlias(Alias alias) {
        return entityManager.merge(alias);
    }

    @Transactional
    public void createAliasesFor(Item item, List<Host> hosts) {
        for (Host host : hosts) {
            String path = item.getPath();
            Alias alias = findAliasByPath(path, host);
            if (alias == null) {
                alias = new Alias();
                alias.setHost(host);
                alias.setPath(path);
            }

            alias.setBundle(item.getBundle());

            saveAlias(alias);
        }
    }

    @Transactional
    public void createAliasesFor(Item item) {
        List<Host> hosts = hostService.getAllHosts();
        createAliasesFor(item, hosts);
    }

}
