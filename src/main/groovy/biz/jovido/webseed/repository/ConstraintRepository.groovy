package biz.jovido.webseed.repository

import biz.jovido.webseed.model.Constraint
import org.springframework.data.jpa.repository.JpaRepository

/**
 *
 * @author Stephan Grundner
 */
interface ConstraintRepository extends JpaRepository<Constraint, Long> {

}