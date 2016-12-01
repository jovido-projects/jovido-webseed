package biz.jovido.seed.content.repository.node;

import biz.jovido.seed.content.model.node.fragment.Property;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Stephan Grundner
 */
public interface PropertyRepository extends JpaRepository<Property, Long> {

}
