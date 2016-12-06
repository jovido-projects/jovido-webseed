package biz.jovido.seed.content.model.node.structure.constraint;

import biz.jovido.seed.content.model.node.fragment.property.TextProperty;
import biz.jovido.seed.content.model.node.structure.Constraints;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author Stephan Grundner
 */
//@Table(name = "numeric_constraint")
@DiscriminatorValue("decimal")
@Entity
public class DecimalConstraints extends Constraints {

    public DecimalConstraints() {
        super(TextProperty.class);
    }
}
