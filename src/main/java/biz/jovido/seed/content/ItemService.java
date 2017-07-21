package biz.jovido.seed.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

/**
 * @author Stephan Grundner
 */
@Service
public class ItemService {

    @Autowired
    private EntityManager entityManager;

    public Chunk createItem(String structureName) {
        Chunk chunk = new Chunk();
        chunk.setStructureName(structureName);

        return chunk;
    }
}
