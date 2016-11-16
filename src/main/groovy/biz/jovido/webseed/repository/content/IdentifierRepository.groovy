package biz.jovido.webseed.repository.content

import biz.jovido.webseed.model.content.Identifier
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

/**
 *
 * @author Stephan Grundner
 */
interface IdentifierRepository extends JpaRepository<Identifier, Long> {

    @Deprecated
    @Query("select max(e.value) from #{#entityName} e where e.type = ?1")
    Long findHighestValueByType(String type)

    Identifier findByTypeAndNextNull(String type)
}