package biz.jovido.seed.model.security

import javax.persistence.*

/**
 *
 * @author Stephan Grundner
 */
@Table(name = '\"user\"')
@Entity
class User {

    @Id
    @GeneratedValue
    Long id

    @Column(unique = true)
    UUID uuid
}
