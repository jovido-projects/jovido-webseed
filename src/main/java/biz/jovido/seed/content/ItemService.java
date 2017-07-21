package biz.jovido.seed.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.Locale;

/**
 * @author Stephan Grundner
 */
@Service
public class ItemService {

    @Autowired
    private EntityManager entityManager;

    public Item createItem(String structureName) {
        Item item = new Item();

        return item;
    }

    public Translation createTranslation(Item item, Locale locale) {
        Translation translation = new Translation();
        translation.setItem(item);
        translation.setLocale(locale);

        return translation;
    }

    public Item saveItem(Item item) {
        return entityManager.merge(item);
    }

    public Translation saveTranslation(Translation translation) {
        return entityManager.merge(translation);
    }
}
