package biz.jovido.webseed.web.content

import biz.jovido.webseed.model.content.Field
import biz.jovido.webseed.model.content.constraint.AlphanumericConstraint
import biz.jovido.webseed.model.content.constraint.NumericConstraint
import biz.jovido.webseed.model.content.constraint.ReferenceConstraint
import org.springframework.stereotype.Component

/**
 *
 * @author Stephan Grundner
 */
@Component('fieldTemplateNameProvider')
class DefaultFieldTemplateNameProvider implements FieldTemplateNameProvider {

    @Override
    String getFieldTemplateName(Field field) {
        assert field != null

        String template

        def constraint = field.constraint
        switch (constraint.class) {
            case AlphanumericConstraint:

                if (((AlphanumericConstraint) constraint).multiline) {
                    template = 'fragment/field/textarea'
                } else {
                    template = 'fragment/field/text'
                }

                break
            case NumericConstraint:
                template = 'fragment/field/number'
                break
            case ReferenceConstraint:
                template = 'fragment/field/text'
                break
            default:
                throw new RuntimeException("Unexpected constraint type [${constraint.class}]")
        }

        template
    }
}
