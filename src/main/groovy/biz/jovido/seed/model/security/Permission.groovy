package biz.jovido.seed.model.security

import javax.persistence.*

/**
 *
 * @author Stephan Grundner
 */
@Table(name = 'permission')
@Entity
class Permission {

    @Id
    @GeneratedValue
    Long id

    @Column(unique = true)
    String name
}
