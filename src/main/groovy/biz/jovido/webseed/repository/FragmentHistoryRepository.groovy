package biz.jovido.webseed.repository

import biz.jovido.webseed.model.FragmentHistory
import org.springframework.data.jpa.repository.JpaRepository

/**
 *
 * @author Stephan Grundner
 */
interface FragmentHistoryRepository extends JpaRepository<FragmentHistory, Long> {

}