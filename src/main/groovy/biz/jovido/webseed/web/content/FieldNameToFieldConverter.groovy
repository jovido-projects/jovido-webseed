package biz.jovido.webseed.web.content

import biz.jovido.webseed.model.content.Field
import org.springframework.core.convert.converter.Converter
import org.springframework.validation.DataBinder

/**
 *
 * @author Stephan Grundner
 */
class FieldNameToFieldConverter implements Converter<String, Field> {

    final DataBinder binder

    FieldNameToFieldConverter(DataBinder binder) {
        this.binder = binder
    }

    @Override
    Field convert(String source) {
        def form = binder.target as FragmentForm
        def fragment = form?.fragment
        def type = fragment?.type
        def field = type?.getField(source)

        field
    }
}
