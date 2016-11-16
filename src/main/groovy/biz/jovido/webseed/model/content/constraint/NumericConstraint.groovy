package biz.jovido.webseed.model.content.constraint

import biz.jovido.webseed.model.content.Constraint
import groovy.transform.CompileStatic

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.Table

/**
 *
 * @author Stephan Grundner
 */
@Table(name = 'numeric_constraint')
@DiscriminatorValue('numeric')
@Entity
@CompileStatic
class NumericConstraint extends Constraint {

}
