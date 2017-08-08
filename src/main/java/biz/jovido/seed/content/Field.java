package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public abstract class Field {

    protected ItemEditor editor;
    protected Attribute attribute;

    public ItemEditor getEditor() {
        return editor;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    protected Item getItem() {
        return editor.getItem();
    }

    public Payload getPayload() {
        return getItem().getPayload(attribute.getName());
    }

    public abstract Object getValue();
    public abstract void setValue(Object value);
}
