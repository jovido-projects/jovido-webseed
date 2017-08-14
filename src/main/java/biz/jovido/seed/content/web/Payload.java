package biz.jovido.seed.content.web;

/**
 * @author Stephan Grundner
 */
public abstract class Payload {

    public enum Type {
        VALUE,
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

    public Payload(Type type) {
        this.type = type;
    }
}
