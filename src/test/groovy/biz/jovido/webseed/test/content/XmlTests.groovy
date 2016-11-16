package biz.jovido.webseed.test.content

import biz.jovido.webseed.service.content.FragmentService
import biz.jovido.webseed.service.content.StructureService
import biz.jovido.webseed.service.content.xml.XmlStructureReader
import biz.jovido.webseed.test.content.config.ModelTestConfiguration
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.InputStreamResource
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional

/**
 *
 * @author Stephan Grundner
 */
@RunWith(SpringRunner)
@SpringBootTest(classes = ModelTestConfiguration)
@TestPropertySource("classpath:application-test.properties")
class XmlTests {

    @Autowired StructureService structureService
    @Autowired FragmentService fragmentService
    @Autowired XmlStructureReader structureReader

    private static final XML1_REVISION_ID = "1.0"
    private static final XML2_REVISION_ID = "2.0"

    def xml1 ="""\
<structure revisionId="$XML1_REVISION_ID">

    <constraints>
        <constraint name="longText" multiline="true" type="alphanumeric">
            <minValues>1</minValues>
            <maxValues>1</maxValues>
        </constraint>
        <alphanumericConstraint name="simpleText">
            <minValues>1</minValues>
            <maxValues>3</maxValues>
        </alphanumericConstraint>
    </constraints>

    <fragmentTypes>
        <fragmentType name="basicPage">
            <field name="title" constraint="simpleText" group="basics" />
            <field name="subtitle" constraint="simpleText" group="basics" />
            <field name="field1" constraint="simpleText" group="group2" />
            <field name="field2" constraint="simpleText" group="group2" />
            <field name="field3" constraint="simpleText" group="group3" />
        </fragmentType>
    </fragmentTypes>

</structure>
"""

    def xml2 ="""\
<structure revisionId="$XML2_REVISION_ID">

    <constraints>
        <constraint name="optionalText" multiline="false" type="alphanumeric">
            <minValues>0</minValues>
            <maxValues>1</maxValues>
        </constraint>
        <alphanumericConstraint name="simpleText">
            <minValues>1</minValues>
            <maxValues>2</maxValues>
        </alphanumericConstraint>
    </constraints>

    <fragmentTypes>
        <fragmentType name="basicPage">
            <field name="title" constraint="simpleText" group="basics" />
            <field name="subtitle" constraint="optionalText" group="basics" />
        </fragmentType>
    </fragmentTypes>

</structure>
"""

    @Rollback(false)
    @Transactional
    @Test
    void test1() {

        def structure1 = structureReader.readStructure(
                new InputStreamResource(
                        new ByteArrayInputStream(
                                xml1.bytes)))

        structure1 = structureService.saveStructure(structure1)
        Assert.assertNotNull(structure1)
        Assert.assertEquals(structure1.revisionId, XML1_REVISION_ID)

        def structure2 = structureReader.readStructure(
                new InputStreamResource(
                        new ByteArrayInputStream(
                                xml2.bytes)))

        structure2 = structureService.saveStructure(structure2)
        Assert.assertNotNull(structure2)
        Assert.assertEquals(structure2.revisionId, XML2_REVISION_ID)

        Assert.assertNull(null)
    }
}
