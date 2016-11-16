package biz.jovido.webseed.service.content

import biz.jovido.webseed.model.content.Structure
import org.springframework.core.io.Resource

/**
 *
 * @author Stephan Grundner
 */
interface StructureReader {

    Structure readStructure(Resource resource)
}