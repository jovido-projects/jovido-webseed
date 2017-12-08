package biz.jovido.seed.content.ui;

import biz.jovido.seed.component.HasTemplate;
import biz.jovido.seed.content.Fragment;
import biz.jovido.seed.content.FragmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * @author Stephan Grundner
 */
@Controller
@RequestMapping("/admin/fragment")
@SessionAttributes(types = FragmentEditorCotroller.FragmentEditor.class)
public class FragmentEditorCotroller {

    public class FragmentEditor implements HasTemplate {

        private String template = "admin/fragment/editor";
        private FragmentForm form = new FragmentForm(fragmentService);

        @Override
        public String getTemplate() {
            return template;
        }

        public void setTemplate(String template) {
            this.template = template;
        }

        public FragmentForm getForm() {
            return form;
        }

        public void setForm(FragmentForm form) {
            this.form = form;
        }

        public Fragment getFragment() {
            return form.getFragment();
        }

        public void setFragment(Fragment fragment) {
            form.setNestedPath("form");
            form.setFragment(fragment);


        }
    }

    @Autowired
    private FragmentService fragmentService;

    @ModelAttribute
    protected FragmentEditor editor() {
        FragmentEditor editor = new FragmentEditor();

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
        editor.setFragment(fragment);

        return "redirect:";
    }
}
