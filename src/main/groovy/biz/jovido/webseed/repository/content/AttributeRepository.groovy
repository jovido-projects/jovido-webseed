package biz.jovido.webseed.repository.content

import biz.jovido.webseed.model.content.Attribute
import biz.jovido.webseed.model.content.Fragment
import org.springframework.data.jpa.repository.JpaRepository

/**
 *
 * @author Stephan Grundner
 */
interface AttributeRepository extends JpaRepository<Attribute, Long> {

    Attribute findByFragmentAndField_Name(Fragment fragment, String fieldName)
//    List<Attribute> findAllByField(Field field)
}