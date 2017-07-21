package biz.jovido.seed;

import biz.jovido.seed.content.Item;
import biz.jovido.seed.content.ItemService;
import biz.jovido.seed.content.Structure;
import biz.jovido.seed.content.StructureService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Locale;

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

        structureService.configure("basicPage")
                .addTextField("title").setCapacity(1)
                .addTextField("subtitle").setCapacity(3)
                .addTextField("summary").setMultiline(true);

        Structure basicPageStructure = structureService.getStructure("basicPage");

        Item basicPage1 = itemService.createItem(basicPageStructure);
        itemService.setValue(basicPage1, "title", 0, Locale.GERMAN, "Willkommen");
        itemService.setValue(basicPage1, "title", 0, Locale.ENGLISH, "Welcome");

        itemService.saveItem(basicPage1);
    }
}
