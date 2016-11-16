package biz.jovido.webseed.repository

import biz.jovido.webseed.model.Fragment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

/**
 *
 * @author Stephan Grundner
 */
interface FragmentRepository extends JpaRepository<Fragment, Long> {

    @Query("select max(e.id) from Fragment e")
    Long getHighestId()
}