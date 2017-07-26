package biz.jovido.seed;

import biz.jovido.seed.content.Item;
import biz.jovido.seed.content.ItemService;
import biz.jovido.seed.content.RelationPayload;
import biz.jovido.seed.content.StructureService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Stephan Grundner
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class)
@DataJpaTest
public class ItemTests {


    @Autowired
    private StructureService structureService;

    @Autowired
    private ItemService itemService;

    @Test
    public void test1() {

        structureService.configure("menuItem")
                .addTextAttribute("title")
                .addRelationAttribute("parent")
                .addRelationAttribute("children");

        structureService.configure("simplePage")
                .addTextAttribute("title")
                .addRelationAttribute("menu");

        Item root = itemService.createItem("menuItem");
        root.setValue("title", "Root Menu Item (not visible)");

        Item menuItem1 = itemService.createItem("menuItem");
        menuItem1.setValue("title", "Menu Item One");

        Item item1 = itemService.createItem("simplePage");
        item1.setValue("title", "Willkommen");
        RelationPayload relation = (RelationPayload) item1.getPayload("menu");
        relation.setValue(menuItem1);






        structureService.configure("textOnlySection")
                .addTextAttribute("text");

        structureService.configure("sectionsPage")
                .addTextAttribute("title")
                .addRelationAttribute("sections");

    }
}
