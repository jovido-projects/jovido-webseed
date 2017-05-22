package biz.jovido.seed.content;

import biz.jovido.seed.content.payload.AssetPayload;
import biz.jovido.seed.content.payload.FragmentPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * @author Stephan Grundner
 */
@Controller
@RequestMapping("/admin/fragments")
@SessionAttributes("fragmentViewState")
public class FragmentFormController {

    @Autowired
    private FragmentService fragmentService;

    @ModelAttribute("fragmentViewState")
    protected FragmentViewState fragmentViewState() {
        return new FragmentViewState();
    }

    @RequestMapping(path = "create")
    protected String create(@ModelAttribute("fragmentViewState") FragmentViewState viewState,
                            BindingResult bindingResult,
                            @RequestParam(name = "structure") String structureName,
                            @RequestParam(name = "locale") Locale locale,
                            RedirectAttributes redirectAttributes) {

        viewState.reset();
        FragmentForm form = new FragmentForm();
        Fragment fragment = fragmentService.createFragment(structureName, locale);
        form.setFragment(fragment);
        viewState.putForm(form);
        viewState.setCurrentForm(form);

        redirectAttributes.addAttribute("form", form.getId());
        return "redirect:show";
    }

    @RequestMapping(path = "show")
    protected String show(@ModelAttribute("fragmentViewState") FragmentViewState viewState,
                          BindingResult bindingResult,
                          @RequestParam(name = "form") String formId) {

        FragmentForm form = viewState.getForm(formId);
        viewState.setCurrentForm(form);

        return "admin/fragment/form";
    }

    @RequestMapping(path = "create-dependent")
    protected String createDependent(@ModelAttribute("fragmentViewState") FragmentViewState viewState,
                                     BindingResult bindingResult,
                                     @RequestParam(name = "structure") String structureName,
                                     @RequestParam(name = "attribute", required = false) String attributeName,
                                     @RequestParam(name = "index", required = false) int valueIndex,
                                     RedirectAttributes redirectAttributes) {

        FragmentForm currentForm = viewState.getCurrentForm();

        FragmentForm.Origin origin = new FragmentForm.Origin();
        origin.setAttributeName(attributeName);
        origin.setValueIndex(valueIndex);
        origin.setForm(currentForm);

        FragmentForm dependentForm = new FragmentForm();
        dependentForm.setOrigin(origin);
        viewState.putForm(dependentForm);

        Locale locale = currentForm.getFragment().getLocale();
        Fragment fragment = fragmentService.createFragment(structureName, locale);
        dependentForm.setFragment(fragment);

        redirectAttributes.addAttribute("form", dependentForm.getId());
        return "redirect:show";
    }

    @RequestMapping(path = "edit")
    protected String edit(@ModelAttribute("fragmentViewState") FragmentViewState viewState,
                          BindingResult bindingResult,
                          @RequestParam(name = "fragment") Long fragmentId,
                          RedirectAttributes redirectAttributes) {

        FragmentForm form = new FragmentForm();
        Fragment fragment = fragmentService.getFragment(fragmentId);
        form.setFragment(fragment);
        viewState.putForm(form);
        viewState.setCurrentForm(form);

        redirectAttributes.addAttribute("form", form.getId());
        return "redirect:show";
    }

    @RequestMapping(path = "edit-dependent")
    protected String editDependent(@ModelAttribute("fragmentViewState") FragmentViewState viewState,
                                   BindingResult bindingResult,
                                   @RequestParam(name = "attribute", required = false) String attributeName,
                                   @RequestParam(name = "index", required = false) int valueIndex,
                                   RedirectAttributes redirectAttributes) {


        FragmentForm currentForm = viewState.getCurrentForm();

        FragmentForm.Origin origin = new FragmentForm.Origin();
        origin.setAttributeName(attributeName);
        origin.setValueIndex(valueIndex);
        origin.setForm(currentForm);

        FragmentForm dependentForm = new FragmentForm();
        dependentForm.setOrigin(origin);
        viewState.putForm(dependentForm);

        Fragment fragment = currentForm.getFragment();
        Attribute attribute = fragment.getAttribute(attributeName);
        FragmentPayload payload = (FragmentPayload) attribute.getPayloads().get(valueIndex);
        Fragment dependentFragment = payload.getValue();
        fragmentService.applyDefaults(dependentFragment);
        dependentForm.setFragment(dependentFragment);

        redirectAttributes.addAttribute("form", dependentForm.getId());
        return "redirect:show";
    }

    @RequestMapping("append-value")
    protected String appendValue(@Valid @ModelAttribute("fragmentViewState") FragmentViewState viewState,
                                 BindingResult bindingResult,
                                 @RequestParam(name = "field") String fieldName,
                                 RedirectAttributes redirectAttributes) {

        FragmentForm form = viewState.getCurrentForm();
        Fragment fragment = form.getFragment();
        Attribute attribute = fragment.getAttribute(fieldName);
        List<Payload<?>> payloads = attribute.getPayloads();
        Payload<?> payload = fragmentService.createPayload(fragment, fieldName);
        payload.setAttribute(attribute);
        payloads.add(payload);

        redirectAttributes.addAttribute("form", form.getId());
        return "redirect:show";
    }

    @RequestMapping("move-value-up")
    protected String moveValueUp(@Valid @ModelAttribute("fragmentViewState") FragmentViewState viewState,
                                 BindingResult bindingResult,
                                 @RequestParam(name = "field") String fieldName,
                                 @RequestParam(name = "index") int index,
                                 RedirectAttributes redirectAttributes) {

        FragmentForm form = viewState.getCurrentForm();
        Fragment fragment = form.getFragment();
        Attribute field = fragment.getAttribute(fieldName);
        List<Payload<?>> payloads = field.getPayloads();
        Collections.swap(payloads, index, index - 1);

        redirectAttributes.addAttribute("form", form.getId());
        return "redirect:show";
    }

    @RequestMapping("move-value-down")
    protected String moveValueDown(@Valid @ModelAttribute("fragmentViewState") FragmentViewState viewState,
                                   BindingResult bindingResult,
                                   @RequestParam(name = "field") String fieldName,
                                   @RequestParam(name = "index") int index,
                                   RedirectAttributes redirectAttributes) {

        FragmentForm form = viewState.getCurrentForm();
        Fragment fragment = form.getFragment();
        Attribute field = fragment.getAttribute(fieldName);
        List<Payload<?>> payloads = field.getPayloads();
        Collections.swap(payloads, index, index + 1);

        redirectAttributes.addAttribute("form", form.getId());
        return "redirect:show";
    }

    @RequestMapping("remove-value")
    protected String removeValue(@Valid @ModelAttribute("fragmentViewState") FragmentViewState viewState,
                                 BindingResult bindingResult,
                                 @RequestParam(name = "field") String fieldName,
                                 @RequestParam(name = "index") int index,
                                 RedirectAttributes redirectAttributes) {

        FragmentForm form = viewState.getCurrentForm();
        Fragment fragment = form.getFragment();
        Attribute field = fragment.getAttribute(fieldName);
        List<Payload<?>> payloads = field.getPayloads();
        payloads.remove(index);

        redirectAttributes.addAttribute("form", form.getId());
        return "redirect:show";
    }

    @RequestMapping("save")
    protected String save(@Valid @ModelAttribute("fragmentViewState") FragmentViewState viewState,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {

        FragmentForm form = viewState.getCurrentForm();

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.fragmentViewState", bindingResult);
            redirectAttributes.addAttribute("form", form.getId());
            return "redirect:show";
        }

        Fragment fragment = form.getFragment();

        fragment = fragmentService.save(fragment);
        form.setFragment(fragment);

        FragmentForm.Origin origin = form.getOrigin();
        if (origin != null) {
            FragmentForm originForm = origin.getForm();
            Fragment originFragment = originForm.getFragment();
            Attribute attribute = originFragment.getAttribute(origin.getAttributeName());
            FragmentPayload payload = (FragmentPayload) attribute.getPayloads().get(origin.getValueIndex());
            payload.setValue(fragment);

            redirectAttributes.addAttribute("form", originForm.getId());
            return "redirect:show";
        }


        redirectAttributes.addAttribute("form", form.getId());
        return "redirect:show";
    }

    @RequestMapping("discard")
    protected String discard(@Valid @ModelAttribute("fragmentViewState") FragmentViewState viewState,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {

        FragmentForm form = viewState.getCurrentForm();

        FragmentForm.Origin origin = form.getOrigin();
        if (origin != null) {
            viewState.removeForm(form);
            FragmentForm originForm = origin.getForm();
            redirectAttributes.addAttribute("form", originForm.getId());
            return "redirect:show";
        }

        redirectAttributes.addAttribute("form", form.getId());
        return "redirect:show";
    }

    @RequestMapping("upload")
    protected String upload(@Valid @ModelAttribute("fragmentViewState") FragmentViewState viewState,
                            BindingResult bindingResult,
                            @RequestParam(name = "field") String fieldName,
                            @RequestParam(name = "index") int index,
                            @RequestParam(name = "asset") MultipartFile multipartFile,
                            RedirectAttributes redirectAttributes) throws IOException {

        FragmentForm form = viewState.getCurrentForm();
        Fragment fragment = form.getFragment();
        AssetPayload payload = PayloadUtils.getPayload(fragment, fieldName, index);
        Asset asset = payload.getValue();
        if (asset == null) {
            asset = new Asset();
        }

        String fileName = multipartFile.getOriginalFilename();
        asset.setFileName(fileName);

        payload.setValue(asset);

        String pathname = String.format("assets/%s.asset",
                asset.getUuid().toString());

        File file = new File(pathname);
        if (!file.getParentFile().exists()) {
            boolean successful = file.getParentFile().mkdir();
            Assert.isTrue(successful);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                FileCopyUtils.copy(inputStream, outputStream);
            }
        }

        redirectAttributes.addAttribute("form", form.getId());
        return "redirect:show";
    }
}
