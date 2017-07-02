package biz.jovido.seed;

import biz.jovido.seed.content.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Locale;

/**
 * @author Stephan Grundner
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class)
@DataJpaTest
public class StructureTests {

    @Autowired
    private ItemService itemService;

    @Autowired
    private HostService hostService;

    @Autowired
    private AliasService aliasService;

    @Autowired
    private StructureService structureService;

    @Test
    public void testBasicPage() {

        hostService.registerHostByName("localhost");
        hostService.registerHostByName("libmori.com");

        Structure basicPage = StructureBuilder
                .create("basicPage")
                .addTextAttribute("title").setCapacity(1).setRequired(1)
                .addTextAttribute("subtitle").setCapacity(2).setRequired(2)
                .addItemAttribute("other").setCapacity(3).setRequired(0)
                .build(structureService);

        Item unsaved = itemService.createNewItem(basicPage, Locale.GERMAN);
        unsaved.setPath("de/welcome");
        unsaved.setValue("title", "Welcome");

        Item saved = itemService.saveItem(unsaved);
        String titleValue = saved.getValue("title");

        itemService.activateItem(saved);

        List<Alias> aliases = aliasService.findAliasesByPath(saved.getPath());
        Alias alias1 = aliasService.findAliasByPath(saved.getPath(), "localhost");
    }
}
