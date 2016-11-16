package biz.jovido.webseed.web.content

import biz.jovido.webseed.service.content.StructureService
import biz.jovido.webseed.web.content.response.StructureResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

/**
 *
 * @author Stephan Grundner
 */
@Controller
class StructureController {

    protected final StructureService structureService

    @Autowired
    StructureController(StructureService structureService) {
        this.structureService = structureService
    }

    @RequestMapping(path = '/structure', produces = ['application/json'])
    @ResponseBody
    StructureResponse index(@RequestParam(name = 'revisionId', required = false) String revisionId) {
        def structure = structureService.getStructure(revisionId)

        new StructureResponse(structure)
    }
}
