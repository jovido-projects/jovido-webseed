package biz.jovido.seed.content.repository.node;

import biz.jovido.seed.content.model.node.fragment.Property;
import biz.jovido.seed.content.model.node.structure.Field;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Stephan Grundner
 */
public interface PropertyRepository extends JpaRepository<Property, Long> {

    List<Property> findAllByField(Field field);
}
