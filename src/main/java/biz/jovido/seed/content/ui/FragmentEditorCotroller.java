package biz.jovido.seed.content.ui;

import biz.jovido.seed.component.HasTemplate;
import biz.jovido.seed.content.*;
import biz.jovido.seed.ui.Field;
import biz.jovido.seed.ui.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@Controller
@RequestMapping("/admin/fragment")
@SessionAttributes(types = FragmentEditorCotroller.FragmentEditor.class)
public class FragmentEditorCotroller {

    public class FragmentEditor implements HasTemplate {

        private String template = "admin/fragment/editor";
        private Fragment fragment;

        private final List<Field<?>> fields = new ArrayList<>();

        @Override
        public String getTemplate() {
            return template;
        }

        public void setTemplate(String template) {
            this.template = template;
        }

        public Fragment getFragment() {
            return fragment;
        }

        public void setFragment(Fragment fragment) {
            this.fragment = fragment;

            fields.clear();


            PayloadList<? extends Payload<String>> titlePayloads = (PayloadList<? extends Payload<String>>)
                    fragment.getPayloadList("title");
            PayloadAttribute<?> titleAttribute = fragmentService.getAttribute(titlePayloads);

            TextPayload title0Payload = (TextPayload) titlePayloads.get(0);
            title0Payload.setValue("Wert");

            FragmentSource.PayloadProperty<String> titleProperty = new FragmentSource.PayloadProperty<>(titlePayloads, titleAttribute.getCapacity());
            TextField titleField = new TextField(titleProperty);
            titleField.setBindingPath("fields[0].value");

            fields.add(titleField);
        }

        public List<Field<?>> getFields() {
            return Collections.unmodifiableList(fields);
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
