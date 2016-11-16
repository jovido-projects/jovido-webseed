package biz.jovido.webseed.repository.content

import biz.jovido.webseed.model.content.Field
import org.springframework.data.jpa.repository.JpaRepository

/**
 *
 * @author Stephan Grundner
 */
interface FieldRepository extends JpaRepository<Field, Long> {

}