package biz.jovido.seed;

import biz.jovido.seed.content.*;
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

        Item item = new Item();
        Property titleProperty = new Property();
        titleProperty.setName("title");
        item.putProperty(titleProperty);

        item = itemService.saveItem(item);

        Translation de = new Translation();
        de.setItem(item);

        TextPayload titlePayload = new TextPayload();
        titlePayload.setText("juhuuuu");

        de.putPayload("title", 0, titlePayload);

        de = itemService.saveTranslation(de);

        Object x = de.getPayload("title", 0);

    }
}
