package biz.jovido.webseed.repository

import biz.jovido.webseed.model.Structure
import org.springframework.data.jpa.repository.JpaRepository

/**
 *
 * @author Stephan Grundner
 */
interface StructureRepository extends JpaRepository<Structure, Long> {

}