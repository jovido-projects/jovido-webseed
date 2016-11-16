package biz.jovido.webseed.model.value

import biz.jovido.webseed.model.Attribute

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.DiscriminatorColumn
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Inheritance
import javax.persistence.InheritanceType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.persistence.Transient

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
