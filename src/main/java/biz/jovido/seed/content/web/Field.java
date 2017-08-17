package biz.jovido.seed.content.web;

import biz.jovido.seed.content.model.Attribute;
import biz.jovido.seed.content.model.Payload;
import biz.jovido.seed.content.model.Structure;

/**
 * @author Stephan Grundner
 */
public abstract class Field {

    private Payload payload;
    private ItemEditor editor;

    public Payload getPayload() {
        return payload;
    }

    /*default*/ void setPayload(Payload payload) {
        this.payload = payload;
    }

    public String getName() {
        return payload.getAttributeName();
    }

    public ItemEditor getEditor() {
        return editor;
    }

    /*default*/ void setEditor(ItemEditor editor) {
        this.editor = editor;
    }

    public Attribute getAttribute() {
        Structure structure = editor.getStructure();
        String attributeName = payload.getAttributeName();
        return structure.getAttribute(attributeName);
    }
}
