package biz.jovido.webseed.test

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
class XmlTests {

    @Autowired StructureService structureService
    @Autowired FragmentService fragmentService

    def xml1 ="""\
<structure>

    <constraints>
        <constraint name="simpleText" type="alphanumeric" revision="1.0">
            <minValues>1</minValues>
            <maxValues>3</maxValues>
        </constraint>
    </constraints>

    <fragmentTypes>
        <fragmentType name="basicPage" revision="1.0">
            <field name="title" constraint="simpleText" />
        </fragmentType>
    </fragmentTypes>

</structure>
"""

    @Rollback(false)
    @Transactional
    @Test
    void test1() {

        Assert.assertNull(null)
    }
}
