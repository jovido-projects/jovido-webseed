package biz.jovido.webseed.repository.content

import biz.jovido.webseed.model.content.FragmentHistory
import org.springframework.data.jpa.repository.JpaRepository

/**
 *
 * @author Stephan Grundner
 */
interface FragmentHistoryRepository extends JpaRepository<FragmentHistory, Long> {

}