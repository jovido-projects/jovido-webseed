package biz.jovido.webseed.repository.content

import biz.jovido.webseed.model.content.FragmentType
import biz.jovido.webseed.model.content.Structure
import org.springframework.data.jpa.repository.JpaRepository

/**
 *
 * @author Stephan Grundner
 */
interface FragmentTypeRepository extends JpaRepository<FragmentType, Long> {

    FragmentType findByStructureAndName(Structure structure, String name)
}