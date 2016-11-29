package biz.jovido.seed.model

import biz.jovido.seed.model.security.User
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

/**
 *
 * @author Stephan Grundner
 */
@EntityListeners(AuditingEntityListener)
trait Auditee {

    @Column(name = 'created')
    @CreatedDate
    Date created

    @Column(name = 'last_modified')
    @LastModifiedDate
    Date lastModified

    @ManyToOne
    @JoinColumn(name = 'created_by_id')
    @CreatedBy
    User createdBy

    @ManyToOne
    @JoinColumn(name = 'last_modified_by_id')
    @LastModifiedBy
    User lastModifiedBy
}