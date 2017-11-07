package biz.jovido.seed.net;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Stephan Grundner
 */
@Repository
public interface HostRepository extends JpaRepository<Host, Long> {

    Host findByName(String name);
}
