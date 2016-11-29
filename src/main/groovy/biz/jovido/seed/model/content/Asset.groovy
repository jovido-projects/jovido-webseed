package biz.jovido.seed.model.content

import biz.jovido.seed.model.Auditee

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

/**
 *
 * @author Stephan Grundner
 */
@Entity
class Asset implements Auditee {

    @Id
    @GeneratedValue
    Long id

    @Column(name = 'file_name', nullable = false)
    String fileName
}
