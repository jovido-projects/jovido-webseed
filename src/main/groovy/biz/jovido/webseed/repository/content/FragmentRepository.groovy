package biz.jovido.webseed.repository.content

import biz.jovido.webseed.model.content.Fragment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

/**
 *
 * @author Stephan Grundner
 */
interface FragmentRepository extends JpaRepository<Fragment, Fragment.FragmentKey> {

    @Query("select max(e.id) from Fragment e")
    Long getHighestId()
}