package biz.jovido.webseed.web.content

import biz.jovido.webseed.model.content.Fragment
import biz.jovido.webseed.model.content.payload.ReferencePayload
import biz.jovido.webseed.service.content.FragmentService
import biz.jovido.webseed.service.content.StringToFragmentHistoryConverter
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.convert.support.ConfigurableConversionService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.Errors
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView

/**
 *
 * @author Stephan Grundner
 */
@SessionAttributes(['editorManager'])
@Controller
class FragmentController {

    static class Editor {

        final String id
        FragmentForm form

        void setForm(FragmentForm form) {
            this.form = form
        }

        ReferencePayload referringPayload

        Editor(String id) {
            this.id = id
        }
    }

    static class EditorManager {

        static int count = 0

        Editor currentEditor
        Stack<Editor> editors = new Stack<>()

        Editor createEditor() {
//            def id = Long.toString(System.nanoTime(), 36)
            def id = Integer.toString(count++)
            currentEditor = new Editor(id)
            editors.push(currentEditor)
        }

        void setCurrentEditor(Editor currentEditor) {
            def position = editors.indexOf(currentEditor)
            if (position >= 0) {
                def n = (editors.size() - position - 1)
                for (int i = 0; i < n; i++) {
                    editors.pop()
                }
            } else {
                throw new IllegalArgumentException()
            }

            this.currentEditor = currentEditor
        }

        Editor getEditor(String id) {
            def editor = editors.find { it.id == id }

            editor
        }

        Editor reset() {
            count = 0
            currentEditor = null
            editors.clear()

            createEditor()
        }
    }

    static abstract class FormProcessingHandler {

        void onSuccess(ModelAndView modelAndView) {}

        void onFailure(ModelAndView modelAndView) {}
    }

    protected final FragmentService fragmentService

    @Autowired
    FragmentController(FragmentService fragmentService) {
        this.fragmentService = fragmentService
    }

    @ModelAttribute('editorManager')
    EditorManager editorManager() {
        new EditorManager()
    }

    @InitBinder
    void initBinder(WebDataBinder binder) {
        def cs = (ConfigurableConversionService) binder.conversionService
        cs.addConverter(new FieldNameToFieldConverter(binder))
        cs.addConverter(new StringToFragmentHistoryConverter(fragmentService))
    }

    @RequestMapping(path = '/fragment', produces = ['application/json'])
    @ResponseBody
    FragmentResponse byId(@RequestParam(name = 'id') String id,
                          @RequestParam(name = 'revision') String revisionId) {

        def fragment = fragmentService.getFragment(id, revisionId)
        new FragmentResponse(fragment)
    }

    FragmentForm createForm(Fragment fragment) {
        def form = new FragmentForm()
        form.fragment = fragment

        def fieldGroups = fragment.type.fieldGroups.values()
        form.fieldGroupName = fieldGroups.empty ? '' : fieldGroups.first().name

        form
    }

    @RequestMapping(path = 'fragment/create')
    String create(@ModelAttribute('editorManager') EditorManager editorManager,
                  @RequestParam(name = 'type') String fragmentTypeName,
                  Model model) {

        def editor = editorManager.reset()

        def fragment = fragmentService.createFragment(fragmentTypeName)
        fragmentService.applyDefaults(fragment)
        def form = createForm(fragment)
        editor.form = form
        model.addAttribute('form', form)

        "fragment/form"
    }

    @RequestMapping(path = 'fragment/edit')
    String edit(@ModelAttribute('editorManager') EditorManager editorManager,
                  @RequestParam(name = 'editor') String editorId,
                  Model model) {

        def editor = editorManager.getEditor(editorId)
        editorManager.currentEditor = editor
        model.addAttribute('form', editor.form)

        "fragment/form"
    }

    protected ModelAndView processForm(FragmentForm form, Errors bindingResult, EditorManager editorManager, FormProcessingHandler handler) {
        def modelAndView = new ModelAndView()

        modelAndView.addObject('form', form)
        modelAndView.viewName = "fragment/form"

        if (bindingResult.hasErrors()) {
            handler.onFailure(modelAndView)
            return modelAndView
        }

        def editor = editorManager.currentEditor
        BeanUtils.copyProperties(form, editor.form, 'fragment')
        fragmentService.copyFragment(form.fragment, editor.form.fragment)

        handler?.onSuccess(modelAndView)

        modelAndView
    }

    @RequestMapping('fragment/extend')
    ModelAndView createAndAssign(@ModelAttribute('editorManager') EditorManager editorManager,
                                 @RequestParam(name = 'field') String fieldName,
                                 @RequestParam(name = 'index') int index,
                                 @RequestParam(name = 'type') String fragmentTypeName,
                          FragmentForm form, BindingResult bindingResult) {
        processForm(form, bindingResult, editorManager, new FormProcessingHandler() {
            @Override
            void onSuccess(ModelAndView modelAndView) {

                def currentFragment = form.fragment
                def currentAttribute = fragmentService.getAttribute(currentFragment, fieldName)
                def referringPayload = currentAttribute.getPayload(index) as ReferencePayload

                def newFragment = fragmentService.createFragment(fragmentTypeName)
                fragmentService.applyDefaults(newFragment)
                def newForm = createForm(newFragment)

                def editor = editorManager.createEditor()
                editor.referringPayload = referringPayload
                editor.form = newForm

                modelAndView.addObject('form', newForm)
            }
        })
    }

    @RequestMapping('fragment/addvalue')
    ModelAndView addValue(@ModelAttribute('editorManager') EditorManager editorManager,
                          @RequestParam(name = 'field') String fieldName,
                          FragmentForm form, BindingResult bindingResult) {
        processForm(form, bindingResult, editorManager, new FormProcessingHandler() {
            @Override
            void onSuccess(ModelAndView modelAndView) {
                def editor = editorManager.currentEditor
                def fragment = editor.form.fragment
                def attribute = fragmentService.getAttribute(fragment, fieldName)
                fragmentService.addValue(attribute, null)
//                modelAndView.viewName = "redirect:edit?editor=${editor.id}"
                modelAndView.addObject('form', editor.form)
            }
        })
    }

    @RequestMapping('fragment/remvalue')
    ModelAndView removeValue(@ModelAttribute('editorManager') EditorManager editorManager,
                          @RequestParam(name = 'field') String fieldName,
                          @RequestParam(name = 'index') int index,
                          FragmentForm form, BindingResult bindingResult) {
        processForm(form, bindingResult, editorManager, new FormProcessingHandler() {
            @Override
            void onSuccess(ModelAndView modelAndView) {
                def editor = editorManager.currentEditor
                def fragment = editor.form.fragment
                def attribute = fragmentService.getAttribute(fragment, fieldName)
                fragmentService.removeValue(attribute, index)
//                modelAndView.viewName = "redirect:edit?editor=${editor.id}"
                modelAndView.addObject('form', editor.form)
            }
        })
    }

    @RequestMapping('fragment/valueup')
    ModelAndView moveValueUp(@ModelAttribute('editorManager') EditorManager editorManager,
                             @RequestParam(name = 'field') String fieldName,
                             @RequestParam(name = 'index') int index,
                             FragmentForm form, BindingResult bindingResult) {
        processForm(form, bindingResult, editorManager, new FormProcessingHandler() {
            @Override
            void onSuccess(ModelAndView modelAndView) {
                def editor = editorManager.currentEditor
                def fragment = editor.form.fragment
                def attribute = fragmentService.getAttribute(fragment, fieldName)
                fragmentService.swapValues(attribute, index, index - 1)
//                modelAndView.viewName = "redirect:edit?editor=${editor.id}"
                modelAndView.addObject('form', editor.form)
            }
        })
    }

    @RequestMapping('fragment/valuedown')
    ModelAndView moveValueDown(@ModelAttribute('editorManager') EditorManager editorManager,
                             @RequestParam(name = 'field') String fieldName,
                             @RequestParam(name = 'index') int index,
                             FragmentForm form, BindingResult bindingResult) {
        processForm(form, bindingResult, editorManager, new FormProcessingHandler() {
            @Override
            void onSuccess(ModelAndView modelAndView) {
                def editor = editorManager.currentEditor
                def fragment = editor.form.fragment
                def attribute = fragmentService.getAttribute(fragment, fieldName)
                fragmentService.swapValues(attribute, index, index + 1)
//                modelAndView.viewName = "redirect:edit?editor=${editor.id}"
                modelAndView.addObject('form', editor.form)
            }
        })
    }

    @RequestMapping('fragment/save')
    ModelAndView save(@ModelAttribute('editorManager') EditorManager editorManager,
                      FragmentForm form, BindingResult bindingResult) {
        processForm(form, bindingResult, editorManager, new FormProcessingHandler() {
            @Override
            void onSuccess(ModelAndView modelAndView) {
                def editor = editorManager.currentEditor
                def fragment = editor.form.fragment

                fragmentService.saveFragment(fragment)

                modelAndView.addObject('form', editor.form)
            }
        })
    }
}
