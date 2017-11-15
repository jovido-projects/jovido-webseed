package biz.jovido.seed.content;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Stephan Grundner
 */
public class ItemValidator implements Validator {

    private final ItemService itemService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Item item = (Item) target;

        Structure structure = itemService.getStructure(item);
        for (String attributeName : structure.getAttributeNames()) {
            Attribute attribute = structure.getAttribute(attributeName);

            PayloadGroup payloadGroup = item.getPayloadGroup(attributeName);

        }
    }

    public ItemValidator(ItemService itemService) {
        this.itemService = itemService;
    }
}
