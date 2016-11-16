package biz.jovido.webseed.repository.content

import biz.jovido.webseed.model.content.Structure
import org.springframework.data.jpa.repository.JpaRepository

/**
 *
 * @author Stephan Grundner
 */
interface StructureRepository extends JpaRepository<Structure, Long> {

    Structure findByRevisionId(String revisionId)
}