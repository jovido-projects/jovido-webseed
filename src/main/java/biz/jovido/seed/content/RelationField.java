package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class RelationField extends Field {

    @Override
    public Object getValue() {
        RelationPayload payload = (RelationPayload) getPayload();
        return payload.getValue();
    }

    @Override
    public void setValue(Object value) {
        RelationPayload payload = (RelationPayload) getPayload();
        payload.setValue(value);
    }

    public Relation getRelation() {
        return (Relation) getValue();
    }
}
