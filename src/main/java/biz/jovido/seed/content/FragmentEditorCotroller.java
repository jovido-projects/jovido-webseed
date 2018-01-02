package biz.jovido.seed.content;

import biz.jovido.seed.ui.Action;
import biz.jovido.seed.ui.Actions;
import biz.jovido.seed.ui.BindingPathProvider;
import biz.jovido.seed.ui.StaticText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Collection;

/**
 * @author Stephan Grundner
 */
@Controller
@RequestMapping("/admin/fragment")
@SessionAttributes(types = FragmentEditor.class)
public class FragmentEditorCotroller {

    @Autowired
    private FragmentService fragmentService;

    private FragmentForm createForm(BindingPathProvider nestedBindingPathProvider) {
        FragmentForm form = new FragmentForm(fragmentService);
        form.setNestedBindingPathProvider(nestedBindingPathProvider);
        form.setPayloadSequenceFieldFactory(new FragmentForm.PayloadSequenceFieldFactory() {
            @Override
            public PayloadSequenceField createPayloadSequenceField(FragmentForm form, PayloadSequence sequence) {
                PayloadSequenceField payloadSequenceField = new PayloadSequenceField(form, sequence);
                payloadSequenceField.setTemplate("admin/fragment/sequence-form-field");

                Actions append = new Actions();
                Attribute attribute = fragmentService.getAttribute(sequence);
                if (attribute instanceof FragmentAttribute) {
                    Collection<Structure> structures = ((FragmentAttribute) attribute).getAssignableStructures();
                    structures.forEach(structure -> {
                        Action a = new Action();
                        a.setText(new StaticText(structure.getName()));
                        a.setUrl(String.format(
                                "/admin/fragment/append?structure=%s&field=%s",
                                structure.getName(), payloadSequenceField.getId()));
                        append.add(a);
                    });
                } else {
                    Action primary = new Action();
                    primary.setText(new StaticText(attribute.getName()));
                    primary.setUrl(String.format(
                            "/admin/fragment/append?field=%s",
                            payloadSequenceField.getId()));
                    append.setPrimary(primary);
                }
                payloadSequenceField.setAppendActions(append);

                payloadSequenceField.setBindingPathProvider(() -> String.format("%s.fields[%d]", form.getNestedBindingPath(), payloadSequenceField.getOrdinal()));
                payloadSequenceField.setPayloadFieldFactory(new PayloadSequenceField.PayloadFieldFactory() {
                    @Override
                    public PayloadField createPayloadField(FragmentForm form, Payload payload) {
                        PayloadSequence sequence = payload.getSequence();
                        PayloadSequenceField parent = form.getField(sequence);
                        PayloadField payloadField = new PayloadField(parent, payload);

                        payloadField.setBindingPathProvider(() ->
                                String.format("%s.fields[%d]", parent.getBindingPath(), payloadField.getOrdinal()));

                        Attribute attribute = fragmentService.getAttribute(payload);

                        if (attribute instanceof TextAttribute) {
                            boolean multiline = ((TextAttribute) attribute).isMultiline();
                            if (multiline) {
                                payloadField.setTemplate("admin/fragment/multiline-text-field");
                            } else {
                                payloadField.setTemplate("admin/fragment/text-field");
                            }

                        } else if (attribute instanceof FragmentAttribute) {
                            payloadField.setTemplate("admin/fragment/nested-fragment-field");
                        } else {
                            payloadField.setTemplate("admin/fragment/payload-field");
                        }

                        if (attribute instanceof FragmentAttribute) {
                            FragmentForm nestedForm = createForm(() ->
                                    String.format("%s.nestedForm", payloadField.getBindingPath()));
                            nestedForm.setFragment(payload.getFragment());
                            payloadField.setNestedForm(nestedForm);
                        }

                        payloadField.setMoveUpAction(new Action());
                        payloadField.setMoveDownAction(new Action());
                        payloadField.setRemoveAction(new Action());

                        Action moveUp = payloadField.getMoveUpAction();
                        moveUp.setUrl("/admin/fragment/move-up?field=" + payloadField.getId());
                        moveUp.setText(new StaticText("Move Up"));
                        moveUp.setDisabled(payloadField.isFirst());

                        Action moveDown = payloadField.getMoveDownAction();
                        moveDown.setUrl("/admin/fragment/move-down?field=" + payloadField.getId());
                        moveDown.setText(new StaticText("Move Down"));
                        moveDown.setDisabled(payloadField.isLast());

                        Action remove = payloadField.getRemoveAction();
                        remove.setUrl("/admin/fragment/remove?field=" + payloadField.getId());
                        remove.setText(new StaticText("Remove"));

                        return payloadField;
                    }
                });

                return payloadSequenceField;
            }
        });

        return form;
    }

    @ModelAttribute
    protected FragmentEditor editor() {
        FragmentEditor editor = new FragmentEditor();
        FragmentForm form = createForm(() -> "form");
        editor.setForm(form);
        return editor;
    }

    @RequestMapping
    protected String index(@ModelAttribute FragmentEditor editor) {

        return "admin/fragment/editor-page";
    }

    @RequestMapping("create")
    protected String create(@ModelAttribute FragmentEditor editor,
                            @RequestParam(name = "structure") String structureName) {

        Fragment fragment = fragmentService.createFragment(structureName);
        fragmentService.setValue(fragment, "title", 0, "Title");

        Fragment other = fragmentService.createFragment("story");
        fragmentService.setValue(fragment, "other", 0, other);
        fragmentService.setValue(other, "title", 0, "Nested title");

        editor.setFragment(fragment);

        return "redirect:";
    }

    @RequestMapping("save")
    protected String save(@ModelAttribute FragmentEditor editor,
                          BindingResult editorBindingResult) {

        Fragment fragment = editor.getFragment();

        return "redirect:";
    }

    @RequestMapping("move-up")
    protected String moveUp(@ModelAttribute FragmentEditor editor,
                            BindingResult editorBindingResult,
                            @RequestParam(name = "field") String fieldId) {

        PayloadField field = (PayloadField) editor.getForm().findField(it -> it.getId().equals(fieldId));
        Payload current = field.getPayload();
        int ordinal = current.getOrdinal();
        current.getSequence().swapPayloads(ordinal, ordinal - 1);

        return "redirect:";
    }

    @RequestMapping("move-down")
    protected String moveDown(@ModelAttribute FragmentEditor editor,
                              BindingResult editorBindingResult,
                              @RequestParam(name = "field") String fieldId) {

        PayloadField field = (PayloadField) editor.getForm().findField(it -> it.getId().equals(fieldId));
        Payload current = field.getPayload();
        int ordinal = current.getOrdinal();
        current.getSequence().swapPayloads(ordinal, ordinal + 1);

        return "redirect:";
    }

    @RequestMapping("remove")
    protected String remove(@ModelAttribute FragmentEditor editor,
                            BindingResult editorBindingResult,
                            @RequestParam(name = "field") String fieldId) {

        PayloadField field = (PayloadField) editor.getForm().findField(it -> it.getId().equals(fieldId));
        Payload current = field.getPayload();
        PayloadSequence sequence = current.getSequence();
        sequence.removePayload(current);

        return "redirect:";
    }

    @RequestMapping("append")
    protected String append(@ModelAttribute FragmentEditor editor,
                            BindingResult editorBindingResult,
                            @RequestParam(name = "field") String fieldId,
                            @RequestParam(name = "structure", required = false) String structureName) {

        PayloadSequenceField field = (PayloadSequenceField) editor.getForm().findField(it -> it.getId().equals(fieldId));
        PayloadSequence sequence = field.getSequence();
        Payload payload = new Payload();
        sequence.addPayload(payload);

        if (StringUtils.hasText(structureName)) {
            Fragment fragment = fragmentService.createFragment(structureName);
            payload.setFragment(fragment);
        }

        return "redirect:";
    }
}
