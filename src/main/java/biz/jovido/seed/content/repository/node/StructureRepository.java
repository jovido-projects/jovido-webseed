package biz.jovido.seed.content.repository.node;

import biz.jovido.seed.content.model.node.Structure;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Stephan Grundner
 */
public interface StructureRepository extends JpaRepository<Structure, Long> {

    Structure findByName(String name);
}
