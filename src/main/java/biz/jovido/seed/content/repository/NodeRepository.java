package biz.jovido.seed.content.repository;

import biz.jovido.seed.content.model.Node;
import biz.jovido.seed.content.model.node.structure.Field;
import biz.jovido.seed.content.model.node.Structure;
import biz.jovido.seed.content.model.node.fragment.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    Page<Node> findAllByStructure(Structure type, Pageable pageable);


    @Query("select n from Node n join n.bundle b where n = b.current")
    Page<Node> findAllNodes(Pageable pageable);

    @Transactional
    @Modifying
    @Query("delete from Property pt where pt.field = ?1")
    int deletePropertiesByField(Field field);

    @Query("select p from Property p where p.field = ?1")
    List<Property> findAllPropertiesByField(Field field);

//    @Query("select p from DateProperty p where ?1 member of p.values")
//    List<Property> findBlaBla(Object foobar);
}
