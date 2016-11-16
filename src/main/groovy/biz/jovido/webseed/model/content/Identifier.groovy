package biz.jovido.webseed.model.content

import javax.persistence.*

/**
 *
 * @author Stephan Grundner
 */
@Table(name = 'identifier', uniqueConstraints =
        @UniqueConstraint(columnNames = ['value', 'type']))
@Entity
class Identifier {

    @Id
    @GeneratedValue
    Long id

    @Column(nullable = false, length = 255)
    String value

    @Column(nullable = false)
    String type

    @OneToOne
    @JoinColumn(nullable = true, updatable = false)
    Identifier previous

    @OneToOne
    @JoinColumn(nullable = true)
    Identifier next
}
