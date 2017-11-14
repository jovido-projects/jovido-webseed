package biz.jovido.seed.content.admin;

import biz.jovido.seed.content.*;
import biz.jovido.seed.ui.Actions;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Stephan Grundner
 */
public class PayloadFieldGroup {

    private final String id = UUID.randomUUID().toString();

    private final NestedItemEditor editor;

    private final String attributeName;
    private final List<PayloadField> fields = new ArrayList<>();

    private String template;
    private Actions actions = new Actions();

    public boolean labelVisible = true;

    public String getId() {
        return id;
    }

    public NestedItemEditor getEditor() {
        return editor;
    }

    public Attribute getAttribute() {
        return editor.itemService.getAttribute(getPayloadGroup());
    }

    public String getAttributeName() {
        return attributeName;
    }

    public List<PayloadField> getFields() {
        List<PayloadField> list = fields.stream()
                .sorted(Comparator.comparingInt(it -> it.getPayload().getOrdinal()))
                .collect(Collectors.toList());

        return Collections.unmodifiableList(list);
    }

    private String getTemplateFor(Payload payload) {
        Attribute attribute = editor.itemService.getAttribute(payload);

        if (attribute instanceof TextAttribute) {
            boolean multiline = ((TextAttribute) attribute).isMultiline();
            if (multiline) {
                return "admin/item/editor/field::multiline-text";
            } else {
                return "admin/item/editor/field::text";
            }
        } else if (attribute instanceof ItemAttribute) {
            return "admin/item/editor/field::item";
        } else if (attribute instanceof LinkAttribute) {
            return "admin/item/editor/field::link";
        } else if (attribute instanceof IconAttribute) {
            return "admin/item/editor/field::icon";
        } else if (attribute instanceof ImageAttribute) {
            return "admin/item/editor/field::image";
        } else if (attribute instanceof YesNoAttribute) {
            return "admin/item/editor/field::yesno";
        } else {
            throw new RuntimeException("Unexpected attribute type: " + attribute.getClass());
        }
    }

    public PayloadField addFieldFor(Payload payload) {
        PayloadField field;

        if (payload instanceof ItemRelation) {
            field = new NestedItemEditor(this, editor.itemService);
        } else {
            field = new PayloadField(this);
        }

        field.setPayload(payload);
        field.setTemplate(getTemplateFor(payload));
        fields.add(field);

        return field;
    }

    public boolean removeFieldFor(Payload payload) {
        return fields.removeIf(it -> it.getPayload() == payload);
    }

    public PayloadField findField(String id) {
        for (PayloadField field : fields) {
            if (field.getId().equals(id)) {
                return field;
            }

            if (field instanceof NestedItemEditor) {
                return ((NestedItemEditor) field).findField(id);
            }
        }

        return null;
    }

    public PayloadGroup getPayloadGroup() {
        return editor.getItem().getPayloadGroup(attributeName);
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Actions getActions() {
        return actions;
    }

    public void setActions(Actions actions) {
        this.actions = actions;
    }

    public boolean isLabelVisible() {
        return labelVisible;
    }

    public void setLabelVisible(boolean labelVisible) {
        this.labelVisible = labelVisible;
    }

    public PayloadFieldGroup(NestedItemEditor editor, String attributeName) {
        this.editor = editor;
        this.attributeName = attributeName;
    }
}
