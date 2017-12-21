package biz.jovido.seed.content.ui;

import biz.jovido.seed.content.Fragment;
import biz.jovido.seed.content.FragmentService;
import biz.jovido.seed.content.Payload;
import biz.jovido.seed.content.structure.Attribute;
import biz.jovido.seed.content.structure.FragmentAttribute;
import biz.jovido.seed.content.structure.TextAttribute;
import biz.jovido.seed.ui.BindingPathProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * @author Stephan Grundner
 */
@Controller
@RequestMapping("/admin/fragment")
@SessionAttributes(types = FragmentEditor.class)
public class FragmentEditorCotroller {

    @Autowired
    private FragmentService fragmentService;

    @ModelAttribute
    protected FragmentEditor editor() {
        FragmentEditor editor = new FragmentEditor(fragmentService);
        FragmentForm form = editor.getForm();
        form.setNestedBindingPath("form");
        form.setFieldFactory(new FragmentForm.PayloadFieldFactory() {
            @Override
            public PayloadField createPayloadField(FragmentForm form, Payload payload) {
                PayloadField field = new PayloadField();
                field.setPayload(payload);
                field.setBindingPathProvider(new BindingPathProvider() {
                    @Override
                    public String getBindingPath() {
                        return String.format("%s.fieldsById[%s]", form.getNestedBindingPath(), field.getId());
                    }
                });

                Attribute attribute = fragmentService.getAttribute(payload);

                if (attribute instanceof TextAttribute) {
                    field.setTemplate("admin/fragment/text-field");
                } else if (attribute instanceof FragmentAttribute) {
                    field.setTemplate("admin/fragment/nested-fragment-field");
                } else {
                    field.setTemplate("admin/fragment/payload-field");
                }

                if (attribute instanceof FragmentAttribute) {
                    FragmentForm nestedForm = new FragmentForm(fragmentService);
                    nestedForm.setNestedBindingPathProvider(new BindingPathProvider() {
                        @Override
                        public String getBindingPath() {
                            return String.format("%s.nestedForm", field.getBindingPath());
                        }
                    });
                    nestedForm.setFragment(payload.getFragment());
                    field.setNestedForm(nestedForm);
                }

                return field;
            }
        });

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

    @RequestMapping(path = "append", params = {"!structure"})
    protected String append(@ModelAttribute FragmentEditor editor,
                            BindingResult editorBindingResult,
                            @RequestParam(name = "field") String fieldId) {

        PayloadField field = (PayloadField) editor.getForm().findField(it -> it.getId().equals(fieldId));

        return "redirect:";
    }

    @RequestMapping(path = "append", params = {"structure"})
    protected String append(@ModelAttribute FragmentEditor editor,
                            BindingResult editorBindingResult,
                            @RequestParam(name = "structure") String structureName,
                            @RequestParam(name = "field") String fieldId) {

//        PayloadSequenceFormField field = (PayloadSequenceFormField) editor.getForm().findField(it -> it.getId().equals(fieldId));
//        PayloadSequence sequence = field.getSequence();
//        Fragment fragment = fragmentService.createFragment(structureName);
//        Payload payload = new Payload();
//        payload.setFragment(fragment);
//        sequence.addPayload(payload);

        return "redirect:";
    }
}
