package biz.jovido.webseed.web.content

import biz.jovido.webseed.model.content.Field

/**
 *
 * @author Stephan Grundner
 */
interface FieldTemplateNameProvider {

    String getFieldTemplateName(Field field)
}