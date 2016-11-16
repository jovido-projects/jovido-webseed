package biz.jovido.webseed.test.content

import biz.jovido.webseed.service.content.FragmentService
import biz.jovido.webseed.service.content.StructureService
import biz.jovido.webseed.test.content.config.ModelTestConfiguration
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.ContextHierarchy
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional

/**
 *
 * @author Stephan Grundner
 */
@ContextHierarchy([@ContextConfiguration(classes = ModelTestConfiguration)])
@RunWith(SpringRunner)
class ModelTests {

    @Autowired StructureService structureService
    @Autowired FragmentService fragmentService

    @Rollback(false)
    @Transactional
    @Test
    void test1() {

//        Step 1

        def randomStructureRevisionId = UUID.randomUUID().toString()
        def structure = structureService.createStructure(randomStructureRevisionId)
        def simplePage = structureService.createFragmentType(structure, 'simplePage')

        def simpleText = structureService.createConstraint(structure, 'alphanumeric', 'simpleText')
        simplePage.putField('title', simpleText)

        structure = structureService.saveStructure(structure)

        Assert.assertNotNull(structure)
        Assert.assertEquals(structure.revisionId, randomStructureRevisionId)
        Assert.assertEquals(structure.fragmentTypes.size(), 1)

//        Step 2

        simpleText = structure.getConstraint('simpleText')
        Assert.assertNotNull(simpleText)
        Assert.assertTrue(simpleText.id > 0)

        simplePage = structure.getFragmentType('simplePage')
        Assert.assertNotNull(simplePage)
        Assert.assertTrue(simplePage.id > 0)

        def page1 = fragmentService.createFragment(simplePage)
        fragmentService.setValue(page1, 'title', Locale.GERMAN, 'Welcome')

        println "structure.revisionId: $randomStructureRevisionId"
        println "fragment.id: $page1.id"

        Assert.assertNull(null)
    }
}
