package biz.jovido.seed.content;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
public class FragmentAdminViewState {

    private final Map<String, FragmentAdminForm> forms = new LinkedHashMap<>();

    private FragmentAdminForm currentForm;

//    private Map<String, FragmentForm> getForms() {
//        return Collections.unmodifiableMap(forms);
//    }

    public FragmentAdminForm getForm(String id) {
        return forms.get(id);
    }

    public FragmentAdminForm getCurrentForm() {
        return currentForm;
    }

    public void setCurrentForm(FragmentAdminForm currentForm) {
        this.currentForm = currentForm;
    }

    public FragmentAdminForm putForm(FragmentAdminForm form) {
        return forms.put(form.getId(), form);
    }

    public boolean removeForm(FragmentAdminForm form) {
        return forms.remove(form.getId(), form);
    }

    public void reset() {
        forms.clear();
        currentForm = null;
    }
}
