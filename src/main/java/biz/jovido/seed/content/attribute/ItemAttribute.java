package biz.jovido.seed.content.attribute;

import biz.jovido.seed.content.Attribute;
import biz.jovido.seed.content.payload.ItemPayload;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@Entity
public class ItemAttribute extends Attribute {

    @ElementCollection
    private List<String> compatibleStructures = new ArrayList<>();

    public List<String> getCompatibleStructures() {
        return compatibleStructures;
    }

    public void setCompatibleStructures(List<String> compatibleStructures) {
        this.compatibleStructures = compatibleStructures;
    }

    @Override
    public ItemPayload createPayload() {
        return new ItemPayload();
    }

    public ItemAttribute(String name) {
        super(name);
    }

    public ItemAttribute() {}
}
