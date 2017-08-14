package biz.jovido.seed.content.web;

/**
 * @author Stephan Grundner
 */
public class EditorValue extends Value {

    private ItemEditor editor;

    public ItemEditor getEditor() {
        return editor;
    }

    public void setEditor(ItemEditor editor) {
        this.editor = editor;
    }

    public EditorValue() {
        super(Type.EDITOR);
    }
}
