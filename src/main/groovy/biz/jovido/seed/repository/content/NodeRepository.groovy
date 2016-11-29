package biz.jovido.seed.repository.content

import biz.jovido.seed.model.content.Node
import biz.jovido.seed.model.content.node.Type
import org.springframework.data.jpa.repository.JpaRepository

/**
 *
 * @author Stephan Grundner
 */
interface NodeRepository extends JpaRepository<Node, Long> {

    List<Node> findAllByType(Type type)
}