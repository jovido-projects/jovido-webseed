package biz.jovido.seed;

import biz.jovido.seed.content.service.ItemService;
import biz.jovido.seed.content.service.StructureService;
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

    }
}
