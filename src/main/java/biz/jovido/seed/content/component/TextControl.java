package biz.jovido.seed.content.component;

import biz.jovido.spring.web.ui.MvcComponent;

/**
 * @author Stephan Grundner
 */
@MvcComponent(name = "control", template = "content/component/text-control")
public class TextControl extends Control {

    public TextControl(String nestedPath) {
        super(nestedPath);
    }
}
