package biz.jovido.seed.content;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

/**
 * @author Stephan Grundner
 */
public class FragmentValidator implements Validator {

    private final ItemService itemService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Fragment.class.isAssignableFrom(clazz);
    }

    protected void validateText(Field field, TextAttribute attribute, List<Payload> payloads, Errors errors) {
        int i = 0;
        for (Payload payload : payloads) {
            String value = (String) payload.getValue();
            if (value.length() > attribute.getMaxLength()) {
                String fieldName = String.format("fields[%s].payloads[%d].value", field.getName(), i++);
                errors.rejectValue(fieldName, "foobar", "Foo Bar");
            }
        }
    }

    public void validate(Fragment fragment, Errors errors) {
        Structure structure = itemService.getStructure(fragment);
        for (Attribute attribute : structure.getAttributes()) {
            Field field = fragment.getField(attribute.getFieldName());
            if (attribute instanceof TextAttribute) {
                validateText(field, (TextAttribute) attribute, field.getPayloads(), errors);
            }
        }
    }

    @Override
    public final void validate(Object target, Errors errors) {
        validate((Fragment) target, errors);
    }

    public FragmentValidator(ItemService itemService) {
        this.itemService = itemService;
    }
}
