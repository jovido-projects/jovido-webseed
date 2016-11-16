package biz.jovido.webseed.repository.content

import biz.jovido.webseed.model.content.Constraint
import org.springframework.data.jpa.repository.JpaRepository

/**
 *
 * @author Stephan Grundner
 */
interface ConstraintRepository extends JpaRepository<Constraint, Long> {

}