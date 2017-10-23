package biz.jovido.seed.content.url;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author Stephan Grundner
 */
@Service
public class AliasService {

    @Autowired
    private AliasRepository aliasRepository;

    public Alias getAlias(Long id) {
        return aliasRepository.findOne(id);
    }

    public Alias getAlias(Host host, String path) {
        return aliasRepository.findByHostAndPath(host, path);
    }

    public Alias saveAlias(Alias alias) {
        return aliasRepository.saveAndFlush(alias);
    }

    public Alias getOrCreateAlias(Host host, String path) {
        Assert.notNull(host, "[host] must not be null");
        Assert.hasText(path, "[path] must be specified");
        Alias alias = getAlias(host, path);
        if (alias == null) {
            alias = new Alias();
            alias.setHost(host);
            alias.setPath(path);
        }

        return alias;
    }
}
