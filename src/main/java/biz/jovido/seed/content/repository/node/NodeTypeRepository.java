package biz.jovido.seed.content.repository.node;

import biz.jovido.seed.content.model.node.Type;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Stephan Grundner
 */
public interface NodeTypeRepository extends JpaRepository<Type, Long> {

    Type findByName(String name);
}
