package biz.jovido.seed.content;

import biz.jovido.seed.hostname.Domain;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author Stephan Grundner
 */
@Service
public class AliasService {

    private final AliasRepository aliasRepository;

    public Alias getAlias(Domain domain, String path) {
        return aliasRepository.findByDomainAndPath(domain, path);
    }

    public Alias findByDomain(Collection<Alias> aliases, Domain domain) {
        return aliases.stream()
                .filter(Objects::nonNull)
                .filter(alias -> alias.getDomain() != null)
                .filter(alias -> Objects.equals(domain.getName(), alias.getDomain().getName()))
                .findFirst()
                .orElse(null);
    }

    public AliasService(AliasRepository aliasRepository) {
        this.aliasRepository = aliasRepository;
    }
}
