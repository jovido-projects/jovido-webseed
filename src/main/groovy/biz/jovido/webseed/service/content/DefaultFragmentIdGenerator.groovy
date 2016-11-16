package biz.jovido.webseed.service.content

import biz.jovido.webseed.repository.content.FragmentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 *
 * @author Stephan Grundner
 */
@Component
class DefaultFragmentIdGenerator implements FragmentIdGenerator {

    protected final FragmentRepository fragmentRepository

    @Autowired
    DefaultFragmentIdGenerator(FragmentRepository fragmentRepository) {
        this.fragmentRepository = fragmentRepository
    }

    @Override
    Long getNextId() {
        def highestId = fragmentRepository.highestId
        if (highestId == null) {
            return 1
        }

        highestId + 1L
    }
}
