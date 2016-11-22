package biz.jovido.webseed.test.content

import biz.jovido.webseed.service.content.FragmentService
import biz.jovido.webseed.service.content.StructureService
import biz.jovido.webseed.service.content.XmlStructureReader
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
class NextXmlTests {

    @Autowired StructureService structureService
    @Autowired FragmentService fragmentService
    @Autowired XmlStructureReader structureReader

    private static final XML1_REVISION_ID = "3.0"

    def xml1 ="""\
<structure revisionId="$XML1_REVISION_ID">

    <constraints>
        <constraint name="longText" multiline="true" type="alphanumeric">
            <minValues>1</minValues>
            <maxValues>1</maxValues>
        </constraint>
        <constraint name="featureItemRef" multiline="true" type="reference">
            <minValues>1</minValues>
            <maxValues>99</maxValues>
        </constraint>
        <alphanumericConstraint name="shortText">
            <minValues>1</minValues>
            <maxValues>99</maxValues>
        </alphanumericConstraint>
    </constraints>

    <fragmentTypes>
        <fragmentType name="featureItem">
            <field name="title" constraint="shortText" group="default" />
            <field name="subtitle" constraint="shortText" group="default" />
            <field name="icon" constraint="shortText" group="default" />
        </fragmentType>
        <fragmentType name="basicPage">
            <field name="title" constraint="shortText" group="default" />
            <field name="subtitle" constraint="shortText" group="default" />
            <field name="text" constraint="longText" group="default" />

            <field name="featureItems" constraint="featureItemRef" group="features" />
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

        Assert.assertNull(null)
    }
}
