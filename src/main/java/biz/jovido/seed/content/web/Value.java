package biz.jovido.seed.content.web;

/**
 * @author Stephan Grundner
 */
public abstract class Value {

    public enum Type {
        PAYLOAD,
        EDITOR
    }

    private final Type type;
    PropertyField field;

    public Type getType() {
        return type;
    }

    public PropertyField getField() {
        return field;
    }

    public Value(Type type) {
        this.type = type;
    }
}
