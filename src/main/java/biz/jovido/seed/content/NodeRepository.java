package biz.jovido.seed.content;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Stephan Grundner
 */
@Repository
public interface NodeRepository extends JpaRepository<Node, Long> {

//    Node findByUuid(UUID uuid);
}
