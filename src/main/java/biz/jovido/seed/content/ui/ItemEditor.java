package biz.jovido.seed.content.ui;

import biz.jovido.seed.content.ui.source.ItemSource;

/**
 * @author Stephan Grundner
 */
public class ItemEditor {

    public static class FieldGroup {

    }

    private ItemSource source;

    public ItemSource getSource() {
        return source;
    }

    public void setSource(ItemSource source) {
        this.source = source;
    }
}
