package biz.jovido.seed.content.repository;

import biz.jovido.seed.content.model.Node;
import biz.jovido.seed.content.model.node.Field;
import biz.jovido.seed.content.model.node.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Stephan Grundner
 */
public interface NodeRepository extends JpaRepository<Node, Long> {

    //    @EntityGraph(value = "node", type = EntityGraph.EntityGraphType.LOAD)
    Node findById(Long id);

    List<Node> findAllByType(Type type);

    @Transactional
    @Modifying
    @Query("delete from Property pt where pt.field = ?1")
    int deletePropertiesByField(Field field);
}
