package biz.jovido.seed.content;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@Entity
@DiscriminatorValue("selection")
public class SelectionPayload extends Payload {

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "selection_value")
    @Column(name = "[value]")
    private final List<String> values = new ArrayList<>();

    public List<String> getValues() {
        return values;
    }

    @Override
    public Payload copy() {
        SelectionPayload copy = new SelectionPayload();
        copy.getValues().addAll(getValues());

        return copy;
    }

    public SelectionPayload() {
        super(PayloadType.SELECTION);
    }
}
