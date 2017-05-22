package biz.jovido.seed.content;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
public class FragmentViewState {

    private final Map<String, FragmentForm> forms = new LinkedHashMap<>();

    private FragmentForm currentForm;

//    private Map<String, FragmentForm> getForms() {
//        return Collections.unmodifiableMap(forms);
//    }

    public FragmentForm getForm(String id) {
        return forms.get(id);
    }

    public FragmentForm getCurrentForm() {
        return currentForm;
    }

    public void setCurrentForm(FragmentForm currentForm) {
        this.currentForm = currentForm;
    }

    public FragmentForm putForm(FragmentForm form) {
        return forms.put(form.getId(), form);
    }

    public boolean removeForm(FragmentForm form) {
        return forms.remove(form.getId(), form);
    }

    public void reset() {
        forms.clear();
        currentForm = null;
    }
}
