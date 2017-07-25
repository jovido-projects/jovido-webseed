package biz.jovido.seed.content;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephan Grundner
 */
public class RelationAttribute extends Attribute {

    private Relation.Type type;

    private final List<String> structureNames = new ArrayList<>();

    public Relation.Type getType() {
        return type;
    }

    public void setType(Relation.Type type) {
        this.type = type;
    }

    public List<String> getStructureNames() {
        return structureNames;
    }

    @Override
    public Payload createPayload() {
        return new RelationPayload();
    }

    public RelationAttribute(String name) {
        super(name);
    }
}
