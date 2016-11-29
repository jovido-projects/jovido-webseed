package biz.jovido.seed.repository.content.node

import biz.jovido.seed.model.content.node.Type
import org.springframework.data.jpa.repository.JpaRepository

/**
 *
 * @author Stephan Grundner
 */
interface NodeTypeRepository extends JpaRepository<Type, Long> {

    Type findByName(String name)
}