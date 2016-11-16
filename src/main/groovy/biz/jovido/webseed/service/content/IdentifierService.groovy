package biz.jovido.webseed.service.content

import biz.jovido.webseed.model.content.Identifier
import biz.jovido.webseed.repository.content.IdentifierRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 *
 * @author Stephan Grundner
 */
@Service
class IdentifierService {

    protected final IdentifierRepository identifierRepository

    @Autowired
    IdentifierService(IdentifierRepository identifierRepository) {
        this.identifierRepository = identifierRepository
    }

    String incrementByOne(String value) {
        Long id = Long.parseLong(value)

        Long.toString(id + 1)
    }

    @Transactional
    synchronized Identifier getNextIdentifier(String type) {
        def id = new Identifier()
        id.type = type

        def previous = identifierRepository.findByTypeAndNextNull(type)
        if (previous == null) {
            id.value = 1
        } else {
            id.value = incrementByOne(previous.value)
            id.previous = previous
            previous.next = id
        }

        id = identifierRepository.save(id)
        assert id != null

        if (previous != null) {
            identifierRepository.save(previous)
        }

        id
    }

    String getNextIdValue(String type) {
        getNextIdentifier(type).value
    }
}
