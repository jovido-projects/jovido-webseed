package biz.jovido.seed.content.model.node.fragment.property;

import biz.jovido.seed.content.model.node.fragment.Property;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@DiscriminatorValue("text")
@Entity
public class TextProperty extends Property<String> {

    @ElementCollection
    @CollectionTable(name = "text_property", joinColumns = @JoinColumn(name = "id"))
    @OrderColumn(name = "ordinal")
    @Column(name = "text_value")
    private final List<String> values = new ArrayList<>();

    @Override
    public List<String> getValues() {
        return values;
    }

    public TextProperty() {
        super(String.class);
    }
}
