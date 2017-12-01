package biz.jovido.seed.ui;

import biz.jovido.seed.component.HasTemplate;
import org.apache.commons.collections4.list.AbstractListDecorator;

import java.util.ArrayList;

/**
 * @author Stephan Grundner
 */
public class Breadcrumbs extends AbstractListDecorator<Breadcrumb> implements HasTemplate {

    private String template = "ui/breadcrumbs";

    @Override
    public String getTemplate() {
        return template;
    }

    public Breadcrumbs() {
        super(new ArrayList<>());
    }
}
