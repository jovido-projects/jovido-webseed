package biz.jovido.seed.content;

/**
 * @author Stephan Grundner
 */
public class ItemEditor {

    public static class Parent {

        private ItemEditor editor;
        private String attributeName;

        public ItemEditor getEditor() {
            return editor;
        }

        public void setEditor(ItemEditor editor) {
            this.editor = editor;
        }

        public String getAttributeName() {
            return attributeName;
        }

        public void setAttributeName(String attributeName) {
            this.attributeName = attributeName;
        }
    }

    private Parent parent;
    private Item item;

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
