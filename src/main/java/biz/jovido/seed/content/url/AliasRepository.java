package biz.jovido.seed.content.url;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Stephan Grundner
 */
@Repository
public interface AliasRepository extends JpaRepository<Alias, Long> {

    Alias findByHostAndPath(Host host, String path);
}
