package biz.jovido.webseed.repository

import biz.jovido.webseed.model.Field
import org.springframework.data.jpa.repository.JpaRepository

/**
 *
 * @author Stephan Grundner
 */
interface FieldRepository extends JpaRepository<Field, Long> {

}