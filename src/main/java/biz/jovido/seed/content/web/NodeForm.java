package biz.jovido.seed.content.web;

import biz.jovido.seed.content.model.Node;
import biz.jovido.seed.content.model.node.Fragment;

import java.util.Locale;

/**
 * @author Stephan Grundner
 */
public class NodeForm {

    private Locale locale;
    private Node node;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Fragment getFragment() {
        if (node == null) {
            return null;
        }

        return node.getFragment(locale);
    }
}
