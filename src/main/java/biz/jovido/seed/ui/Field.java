package biz.jovido.seed.ui;

import biz.jovido.seed.component.HasTemplate;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * @author Stephan Grundner
 */
public abstract class Field implements HasTemplate {

    private String id;
    private String template;
    private BindingPathProvider bindingPathProvider;

    public String getId() {
        if (StringUtils.isEmpty(id)) {
            id = UUID.randomUUID().toString();
        }

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public BindingPathProvider getBindingPathProvider() {
        return bindingPathProvider;
    }

    public void setBindingPathProvider(BindingPathProvider bindingPathProvider) {
        this.bindingPathProvider = bindingPathProvider;
    }

    public String getBindingPath() {
        if (bindingPathProvider != null) {
            return bindingPathProvider.getBindingPath();
        }

        return null;
    }

    public void setBindingPath(String bindingPath) {
        bindingPathProvider = new FixedBindingPathProvider(bindingPath);
    }
}
