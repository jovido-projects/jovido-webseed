package biz.jovido.seed.content.admin;

import biz.jovido.seed.content.Payload;

import java.util.UUID;

/**
 * @author Stephan Grundner
 */
public class PayloadField {

    private final String id = UUID.randomUUID().toString();

    private final PayloadFieldGroup group;
    private Payload payload;
    private String template;
    private boolean collapsed;

    public String getId() {
        return id;
    }

    public PayloadFieldGroup getGroup() {
        return group;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public boolean isCollapsed() {
        return collapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }

    public PayloadField(PayloadFieldGroup group) {
        this.group = group;
    }
}
