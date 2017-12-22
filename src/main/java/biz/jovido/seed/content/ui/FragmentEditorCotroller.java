package biz.jovido.seed.content.ui;

import biz.jovido.seed.content.*;
import biz.jovido.seed.ui.Action;
import biz.jovido.seed.ui.Actions;
import biz.jovido.seed.ui.BindingPathProvider;
import biz.jovido.seed.ui.StaticText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
        form.setFieldFactory(new FragmentForm.PayloadFieldFactory() {
            @Override
            public PayloadField createPayloadField(FragmentForm form, Payload payload) {
                PayloadField field = new PayloadField();

                field.setBindingPathProvider(() ->
                        String.format("%s.fieldsById[%s]", form.getNestedBindingPath(), field.getId()));

                Attribute attribute = fragmentService.getAttribute(payload);

                if (attribute instanceof TextAttribute) {
                    field.setTemplate("admin/fragment/text-field");
                } else if (attribute instanceof FragmentAttribute) {
                    field.setTemplate("admin/fragment/nested-fragment-field");
                } else {
                    field.setTemplate("admin/fragment/payload-field");
                }

                if (attribute instanceof FragmentAttribute) {
                    FragmentForm nestedForm = createForm(() ->
                            String.format("%s.nestedForm", field.getBindingPath()));
                    nestedForm.setFragment(payload.getFragment());
                    field.setNestedForm(nestedForm);
                }

                field.setPayloadChangeHandler(new PayloadField.PayloadChangeHandler() {
                    @Override
                    public void payloadChanged(PayloadField field, Payload previous) {
                        field.setMoveUpAction(new Action());
                        field.setMoveDownAction(new Action());
                        field.setRemoveAction(new Action());
                        field.setAppendActions(new Actions());

                        Action moveUp = field.getMoveUpAction();
                        moveUp.setUrl("/admin/fragment/move-up?field=" + field.getId());
                        moveUp.setText(new StaticText("Move Up"));
                        moveUp.setDisabled(field.isFirst());

                        Action moveDown = field.getMoveDownAction();
                        moveDown.setUrl("/admin/fragment/move-down?field=" + field.getId());
                        moveDown.setText(new StaticText("Move Down"));
                        moveDown.setDisabled(field.isLast());

                        Action remove = field.getRemoveAction();
                        remove.setUrl("/admin/fragment/remove?field=" + field.getId());
                        remove.setText(new StaticText("Remove"));

                        Actions append = field.getAppendActions();
                        if (attribute instanceof FragmentAttribute) {
                            Collection<Structure> structures = ((FragmentAttribute) attribute).getAssignableStructures();
                            structures.forEach(structure -> {
                                Action a = new Action();
                                a.setText(new StaticText(structure.getName()));
                                a.setUrl(String.format(
                                        "/admin/fragment/append?structure=%s&field=%s",
                                        structure.getName(), field.getId()));
                                append.add(a);
                            });
                        } else {
                            Action primary = new Action();
                            primary.setText(new StaticText(attribute.getName()));
                            append.setPrimary(primary);
                        }

                        if (payload != null) {
                            payload.addChangeListener(new PayloadChangeListener() {
                                @Override
                                public void payloadChanged(PayloadChange change) {
                                    if (change instanceof OrdinalSet) {
                                        OrdinalSet ordinalSet = (OrdinalSet) change;

                                        Action moveUp = field.getMoveUpAction();
                                        moveUp.setDisabled(field.isFirst());

                                        Action moveDown = field.getMoveDownAction();
                                        moveDown.setDisabled(field.isLast());
                                    }
                                }
                            });
                        }
                    }
                });
                field.setPayload(payload);

                return field;
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

        PayloadField field = editor.getForm().findField(it -> it.getId().equals(fieldId));
        Payload current = field.getPayload();
        int ordinal = current.getOrdinal();
        current.getSequence().swapPayloads(ordinal, ordinal - 1);

        return "redirect:";
    }

    @RequestMapping("move-down")
    protected String moveDown(@ModelAttribute FragmentEditor editor,
                              BindingResult editorBindingResult,
                              @RequestParam(name = "field") String fieldId) {

        PayloadField field = editor.getForm().findField(it -> it.getId().equals(fieldId));
        Payload current = field.getPayload();
        int ordinal = current.getOrdinal();
        current.getSequence().swapPayloads(ordinal, ordinal + 1);

        return "redirect:";
    }

    @RequestMapping("remove")
    protected String remove(@ModelAttribute FragmentEditor editor,
                            BindingResult editorBindingResult,
                            @RequestParam(name = "field") String fieldId) {

        PayloadField field = editor.getForm().findField(it -> it.getId().equals(fieldId));
        Payload current = field.getPayload();
        PayloadSequence sequence = current.getSequence();
        sequence.removePayload(current);

        return "redirect:";
    }

    @RequestMapping(path = "append", params = {"!structure"})
    protected String append(@ModelAttribute FragmentEditor editor,
                            BindingResult editorBindingResult,
                            @RequestParam(name = "field") String fieldId) {

        PayloadField field = editor.getForm().findField(it -> it.getId().equals(fieldId));

        return "redirect:";
    }

    @RequestMapping(path = "append", params = {"structure"})
    protected String append(@ModelAttribute FragmentEditor editor,
                            BindingResult editorBindingResult,
                            @RequestParam(name = "structure") String structureName,
                            @RequestParam(name = "field") String fieldId) {

        PayloadField field = editor.getForm().findField(it -> it.getId().equals(fieldId));
        PayloadSequence sequence = field.getPayload().getSequence();

        Fragment fragment = fragmentService.createFragment(structureName);
        Payload payload = new Payload();
        payload.setFragment(fragment);
        sequence.addPayload(payload);

        return "redirect:";
    }
}
