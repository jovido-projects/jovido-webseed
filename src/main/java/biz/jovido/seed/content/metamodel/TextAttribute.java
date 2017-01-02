package biz.jovido.seed.content.metamodel;

import biz.jovido.seed.content.model.Fragment;
import biz.jovido.seed.content.model.TextType;

/**
 * @author Stephan Grundner
 */
public class TextAttribute<F extends Fragment> extends Attribute<F, String> {

    private final TextType textType;

    public TextType getTextType() {
        return textType;
    }

    public TextAttribute(String name, TextType textType) {
        super(name, String.class);

        this.textType = textType;
    }
}
