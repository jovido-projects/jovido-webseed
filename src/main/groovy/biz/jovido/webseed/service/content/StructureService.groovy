package biz.jovido.webseed.service.content

import biz.jovido.webseed.model.content.Constraint
import biz.jovido.webseed.model.content.FragmentType
import biz.jovido.webseed.model.content.Structure
import biz.jovido.webseed.model.content.constraint.AlphanumericConstraint
import biz.jovido.webseed.model.content.constraint.NumericConstraint
import biz.jovido.webseed.model.content.constraint.ReferenceConstraint
import biz.jovido.webseed.repository.content.ConstraintRepository
import biz.jovido.webseed.repository.content.FragmentTypeRepository
import biz.jovido.webseed.repository.content.StructureRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Service

/**
 *
 * @author Stephan Grundner
 */
@Service
@ConfigurationProperties('webseed.structure')
class StructureService {

    private static final Logger LOG = LoggerFactory.getLogger(StructureService)

    protected final StructureRepository structureRepository
    protected final ConstraintRepository constraintRepository
    protected final FragmentTypeRepository fragmentTypeRepository

    @Deprecated
    String revisionId

    @Autowired
    StructureService(StructureRepository structureRepository,
                     ConstraintRepository constraintRepository,
                     FragmentTypeRepository fragmentTypeRepository) {
        this.structureRepository = structureRepository
        this.constraintRepository = constraintRepository
        this.fragmentTypeRepository = fragmentTypeRepository
    }

    Structure createStructure(String revisionId) {
        def structure = new Structure()
        structure.revisionId = revisionId

        structure
    }

    Structure getStructure(String revisionId) {
        structureRepository.findByRevisionId(revisionId)
    }

    Structure getActiveStructure() {
        getStructure(revisionId)
    }

    Structure saveStructure(Structure structure) {
        structureRepository.save(structure)
    }

    Constraint createConstraint(String type) {
        Constraint constraint

        switch (type) {
            case 'alphanumeric':
                constraint = new AlphanumericConstraint()
                break
            case 'numeric':
                constraint = new NumericConstraint()
                break
            case 'reference':
                constraint = new ReferenceConstraint()
                break
            default:
                throw new IllegalArgumentException("Unexpected type [$type]")
        }

        constraint
    }

    @Deprecated
    Constraint createConstraint(Structure structure, String type, String name) {
        assert structure != null

        Constraint constraint

        switch (type) {
            case 'alphanumeric':
                constraint = new AlphanumericConstraint()
                break
            case 'numeric':
                constraint = new NumericConstraint()
                break
//            case 'reference'
//                constraint =
//                break
            default:
                throw new IllegalArgumentException("Unexpected type [$type]")
        }

        constraint.name = name
        constraint.structure = structure

        constraint
    }

    Constraint saveConstraint(Constraint constraint) {
        constraintRepository.save(constraint)
    }

    FragmentType createFragmentType(Structure structure, String name) {
        assert structure != null
        def fragmentType = new FragmentType()

        fragmentType.name = name
        fragmentType.structure = structure


        fragmentType
    }

    FragmentType getFragmentType(Long id) {
        fragmentTypeRepository.findOne(id)
    }

    FragmentType getFragmentType(Structure structure, String name) {
        def fragmentType = fragmentTypeRepository.findByStructureAndName(structure, name)

        fragmentType
    }

    FragmentType getFragmentType(String name) {
        getFragmentType(activeStructure, name)
    }

    FragmentType saveFragmentType(FragmentType type) {
        fragmentTypeRepository.save(type)
    }
}
