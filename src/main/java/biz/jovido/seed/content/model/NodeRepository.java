package biz.jovido.seed.content.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Stephan Grundner
 */
@Repository
public interface NodeRepository extends JpaRepository<Node, Long> {

//    List<Node> findAllByItemAndRoot_Hierarchy_Name(Item item, String hierarchyName);
}
