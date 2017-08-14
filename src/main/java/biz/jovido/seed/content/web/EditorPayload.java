package biz.jovido.seed.content.web;

/**
 * @author Stephan Grundner
 */
public class EditorPayload extends Payload {

    private ItemEditor editor;

    public ItemEditor getEditor() {
        return editor;
    }

    public void setEditor(ItemEditor editor) {
        this.editor = editor;
    }

    public EditorPayload() {
        super(Type.EDITOR);
    }
}
