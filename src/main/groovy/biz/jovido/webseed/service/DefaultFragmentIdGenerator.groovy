package biz.jovido.webseed.service

import biz.jovido.webseed.repository.FragmentRepository
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
            return 0
        }

        highestId + 1L
    }
}
