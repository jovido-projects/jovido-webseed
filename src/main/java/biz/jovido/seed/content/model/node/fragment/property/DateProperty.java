package biz.jovido.seed.content.model.node.fragment.property;

import biz.jovido.seed.content.model.node.fragment.Property;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@DiscriminatorValue("date")
@Entity
public class DateProperty extends Property<Date> {

    @ElementCollection
    @CollectionTable(name = "date_property", joinColumns = @JoinColumn(name = "id"))
    @OrderColumn(name = "ordinal")
    @Column(name = "date_value")
    private final List<Date> values = new ArrayList<>();

    @Override
    public List<Date> getValues() {
        return values;
    }

    public DateProperty() {
        super(Date.class);
    }
}
