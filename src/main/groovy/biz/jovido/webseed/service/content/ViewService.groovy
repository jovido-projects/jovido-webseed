package biz.jovido.webseed.service.content

import biz.jovido.webseed.model.content.Field
import biz.jovido.webseed.model.content.constraint.AlphanumericConstraint
import biz.jovido.webseed.web.content.FieldTemplateNameProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 *
 * @author Stephan Grundner
 */
@Component
class ViewService {

    final FragmentService fragmentService
    final FieldTemplateNameProvider fieldTemplateNameProvider

    @Autowired
    ViewService(FragmentService fragmentService, FieldTemplateNameProvider fieldTemplateNameProvider) {
        this.fragmentService = fragmentService
        this.fieldTemplateNameProvider = fieldTemplateNameProvider
    }

    String getTemplate(Field field) {
        fieldTemplateNameProvider.getFieldTemplateName(field)
    }

    boolean requiresMuchSpace(Field field) {
        def constraint = field.constraint
        if (constraint instanceof AlphanumericConstraint) {
            return constraint.multiline
        }

        false
    }
}
