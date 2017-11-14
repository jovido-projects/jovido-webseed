package biz.jovido.seed.content.admin;

import biz.jovido.seed.content.Payload;
import biz.jovido.seed.content.PayloadGroup;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public String getCaption() {
        if (getGroup() != null) {
            return getGroup().getAttributeName() + " " + (getPayload().getOrdinal() + 1);
        }

        return "";
    }

    public List<PayloadField> getTrail() {
        LinkedList<PayloadField> trail = new LinkedList<>();

        PayloadField field = this;
        do {
            PayloadFieldGroup group = field.getGroup();
            if (group != null) {
                trail.addFirst(field);
            }


            field = group != null ? group.getEditor() : null;
        } while (field != null);

        return trail;
    }

    public String getTrailAsText() {
        StringBuilder text = new StringBuilder();
        for (PayloadField field : getTrail()) {
            text.append(field.getCaption()).append(" / ");
        }

        return StringUtils.removeEnd(text.toString(), " / ");
    }

    public PayloadField(PayloadFieldGroup group) {
        this.group = group;
    }
}
