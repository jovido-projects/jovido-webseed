package biz.jovido.webseed.test

import biz.jovido.webseed.service.FragmentIdGenerator
import biz.jovido.webseed.service.FragmentService
import biz.jovido.webseed.service.StructureService
import biz.jovido.webseed.test.config.ModelTestConfiguration
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
    @Autowired FragmentIdGenerator fragmentIdGenerator

    @Rollback(false)
    @Transactional
    @Test
    void test1() {

        def structure1 = structureService.createStructure("1.0")
        def simpleText = structureService.createConstraint(structure1, 'alphanumeric', 'simpleText')
        def simplePage = structureService.createFragmentType(structure1, 'simplePage')
        simplePage.putField('title', simpleText)

        def page1 = fragmentService.createFragment(simplePage)
        def nextId = fragmentIdGenerator.nextId

        fragmentService.setValue(page1, 'title', Locale.GERMAN, 'Welcome')

        Assert.assertNull(null)
    }
}
