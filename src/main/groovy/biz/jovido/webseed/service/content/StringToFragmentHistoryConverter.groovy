package biz.jovido.webseed.service.content

import biz.jovido.webseed.model.content.FragmentHistory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils

/**
 *
 * @author Stephan Grundner
 */
@Component
class StringToFragmentHistoryConverter implements Converter<String, FragmentHistory> {

    final FragmentService fragmentService

    @Autowired
    StringToFragmentHistoryConverter(FragmentService fragmentService) {
        this.fragmentService = fragmentService
    }

    @Override
    FragmentHistory convert(String source) {
        if (StringUtils.isEmpty(source)) {
            return null
        }

        throw new UnsupportedOperationException()
    }
}
