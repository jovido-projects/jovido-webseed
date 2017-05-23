package biz.jovido.seed.content;

import biz.jovido.seed.hostname.Domain;
import org.springframework.stereotype.Service;

/**
 * @author Stephan Grundner
 */
@Service
public class AliasService {

    private final AliasRepository aliasRepository;

    public Alias getAlias(Domain domain, String path) {
        return aliasRepository.findByDomainAndPath(domain, path);
    }

    public AliasService(AliasRepository aliasRepository) {
        this.aliasRepository = aliasRepository;
    }
}
