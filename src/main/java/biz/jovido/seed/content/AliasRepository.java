package biz.jovido.seed.content;

import biz.jovido.seed.hostname.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Stephan Grundner
 */
@Repository
public interface AliasRepository extends JpaRepository<Alias, Long> {

    Alias findByDomainAndPath(Domain domain, String path);
}
