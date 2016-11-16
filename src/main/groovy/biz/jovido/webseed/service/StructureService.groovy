package biz.jovido.webseed.service

import biz.jovido.webseed.model.Constraint
import biz.jovido.webseed.model.FragmentType
import biz.jovido.webseed.model.Structure
import biz.jovido.webseed.model.constraint.AlphanumericConstraint
import biz.jovido.webseed.model.constraint.NumericConstraint
import biz.jovido.webseed.repository.ConstraintRepository
import biz.jovido.webseed.repository.FragmentTypeRepository
import biz.jovido.webseed.repository.StructureRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 *
 * @author Stephan Grundner
 */
@Service
class StructureService {

    private static final Logger LOG = LoggerFactory.getLogger(StructureService)

    protected final StructureRepository structureRepository
    protected final ConstraintRepository constraintRepository
    protected final FragmentTypeRepository fragmentTypeRepository

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

    Structure saveStructure(Structure structure) {
        structureRepository.save(structure)
    }

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

        constraint.structure = structure
        constraint.name = name

        constraint
    }

    Constraint saveConstraint(Constraint constraint) {
        constraintRepository.save(constraint)
    }

    FragmentType createFragmentType(Structure structure, String name) {
        assert structure != null
        def fragmentType = new FragmentType()
        fragmentType.structure = structure
        fragmentType.name = name

        fragmentType
    }

    FragmentType saveFragmentType(FragmentType type) {
        fragmentTypeRepository.save(type)
    }
}
