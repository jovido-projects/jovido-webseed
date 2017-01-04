package biz.jovido.seed.content.repository;

import biz.jovido.seed.content.domain.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Stephan Grundner
 */
@Repository
public interface NodeRepository extends JpaRepository<Node, Long> {
}
