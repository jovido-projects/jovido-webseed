package biz.jovido.webseed.model.content

import javax.persistence.*

/**
 *
 * @author Stephan Grundner
 */
@Table(name = 'payload')
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = 'type')
@Entity
abstract class Payload<T> {

    @Id
    @GeneratedValue
    Long id

    @Column(nullable = false)
    int ordinal = 0

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    public Attribute attribute

    @Transient
    abstract T getValue()
    abstract void setValue(T value)
}
