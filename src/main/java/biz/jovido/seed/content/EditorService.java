package biz.jovido.seed.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Stephan Grundner
 */
@Service
public class EditorService {

    @Autowired
    private ItemService itemService;
}
