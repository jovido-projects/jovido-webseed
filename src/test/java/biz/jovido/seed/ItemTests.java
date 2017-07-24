package biz.jovido.seed;

import biz.jovido.seed.content.Item;
import biz.jovido.seed.content.ItemService;
import biz.jovido.seed.content.OneToManyRelation;
import org.junit.Assert;
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
    private ItemService itemService;

    @Test
    public void test1() {

        final String WILLKOMMEN = "Willkommen";

//        Item de = itemService.createItem(Locale.GERMAN);
//
//        de.setValue("title", WILLKOMMEN);
//
//        OneToManyRelation children = new OneToManyRelation();
//        children.setOwner(de);
//        children.getMany().add(de);
//        children.getMany().add(de);
//        de.setValue("children", children);
//
//        de = itemService.saveItem(de);
//
//        String value = (String) de.getValue("title");
//        Assert.assertEquals(value, WILLKOMMEN);


    }
}
