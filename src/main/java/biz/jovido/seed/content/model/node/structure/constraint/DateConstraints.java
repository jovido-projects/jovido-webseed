package biz.jovido.seed.content.model.node.structure.constraint;

import biz.jovido.seed.content.model.node.fragment.property.DateProperty;
import biz.jovido.seed.content.model.node.structure.Constraints;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

/**
 * @author Stephan Grundner
 */
//@Table(name = "temporal_constraint")
@DiscriminatorValue("date")
@Entity
public class DateConstraints extends Constraints {

    public DateConstraints() {
        super(DateProperty.class);
    }
}
