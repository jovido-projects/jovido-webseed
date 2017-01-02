package biz.jovido.seed.content.component;

import biz.jovido.spring.web.ui.MvcComponent;

/**
 * @author Stephan Grundner
 */
@MvcComponent("content/component/label")
public class Label {

    public static interface TextResolver {

        String resolveText();
    }

    private TextResolver textResolver;

    public TextResolver getTextResolver() {
        return textResolver;
    }

    public void setTextResolver(TextResolver textResolver) {
        this.textResolver = textResolver;
    }

    public String getText() {
        return textResolver.resolveText();
    }

    void setText(String text) {
        textResolver = () -> text;
    }

    public Label(String text) {
        setText(text);
    }

    public Label() {}
}
