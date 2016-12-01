package biz.jovido.seed.content

import biz.jovido.seed.content.service.NodeService
import biz.jovido.seed.content.service.node.TypeBuilder
import groovy.transform.CompileStatic
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional

/**
 *
 * @author Stephan Grundner
 */
@RunWith(SpringRunner)
@EnableAutoConfiguration
@SpringBootTest(classes = Config)
//@EnableConfigurationProperties
@CompileStatic
class ModelTests {

    @ComponentScan('biz.jovido.seed.service')
    @EntityScan('biz.jovido.seed.model')
    @EnableJpaRepositories('biz.jovido.seed.repository')
    @EnableJpaAuditing
    @Configuration
    static class Config { }

    @Autowired
    NodeService contentService

    static boolean isUnmodifiable(Map map) {
        map.getClass().isInstance(Collections.unmodifiableMap([:]))
    }

    static boolean isUnmodifiable(Set set) {
        set.getClass().isInstance(Collections.unmodifiableCollection(new HashSet()))
    }

    static boolean isUnmodifiable(List list) {
        list.getClass().isInstance(Collections.unmodifiableCollection(new ArrayList()))
    }

    @Test
    @Transactional
    void testAlteringStructure() {
        def basicPage = new TypeBuilder()
                .setName('basicPage')
                .addAlphanumericField('title')
                    .setHtml(true)
                    .setMultiline(true)
                    .setMinimumNumberOfItems(1)
                .addNodeField('self')
                    .withStructureName('basicPage')
                .getType()

        contentService.saveType(basicPage)

        basicPage = new TypeBuilder()
                .setName('basicPage')
                .addAlphanumericField('title')
                    .setHtml(true)
                    .setMultiline(true)
                    .setMinimumNumberOfItems(1)
//                .addNodeField('self')
//                    .withStructureName('basicPage')
                .getType()

        contentService.saveType(basicPage)

        Assert.assertTrue(true)
    }

//    String createAndSaveBasicPage() {
//        def basicPage = new StructureBuilder()
//                .setName('basicPage')
//                .addAlphanumericField('title')
//                    .setHtml(true)
//                    .setMultiline(true)
//                    .setMinimumNumberOfPayloads(1)
//                .addNodeField('self')
//                    .withStructureName('basicPage')
//                .getType()
//
//        contentService.saveType(basicPage)
//
//        basicPage.name
//    }
//
//    Content createAndSaveNode(Structure type) {
//        def node = contentService.createNode(type)
//
//        type.fields.each { name, field ->
//            nodeService.createAttribute(field, node, 1)
//        }
//
//        contentService.saveFragment(node)
//    }
//
//    Fragment createAndSaveFragment(Long nodeId, Locale locale, String title) {
//        def node = contentService.getFragment(nodeId)
//        def content = nodeService.createNode(node, locale)
//        nodeService.setValue('title', content, TextPayload, title)
//        nodeService.setValue('self', content, ReferencePayload, content)
//
//        nodeService.saveFragment(content)
//    }
//
//    @Rollback(true)
//    @Transactional
//    @Test
//    void simpleModelTest() {
//
//        def basicPageName = createAndSaveBasicPage()
//        def basicPage = contentService.getType(basicPageName)
//        def page1 = createAndSaveNode(basicPage)
//
//        def page1_en = createAndSaveFragment(page1.id, Locale.ENGLISH, 'Welcome')
//        def page1_de = createAndSaveFragment(page1.id, Locale.GERMAN, 'Willkommen')
//
//        def titleValue_en = nodeService.getValue('title', page1_en)
//        def selfValue_en = nodeService.getValue('self', page1_en)
//
//        def titleValue_de = nodeService.getValue('title', page1_de)
//        def titleValue_de1 = nodeService.getValue('title', 1, page1_de)
//        def titleValue_de2 = nodeService.getValue('title', 2, page1_de)
//        def selfValue_de = nodeService.getValue('self', page1_de)
//
//        Assert.assertTrue(true)
//    }
}
