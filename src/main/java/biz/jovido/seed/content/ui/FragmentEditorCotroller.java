package biz.jovido.seed.content.ui;

import biz.jovido.seed.content.Fragment;
import biz.jovido.seed.content.FragmentService;
import biz.jovido.seed.content.Payload;
import biz.jovido.seed.ui.Field;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
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
        field.getGroup().invalidate();

        return "redirect:";
    }

    @RequestMapping("append")
    protected String append(@ModelAttribute FragmentEditor editor,
                            BindingResult editorBindingResult,
                            @RequestParam(name = "field") String fieldId) {

        PayloadField field = editor.getForm().findField(it -> it.getId() == fieldId);

        return "redirect:";
    }
}
