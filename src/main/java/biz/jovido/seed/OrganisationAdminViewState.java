package biz.jovido.seed;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Stephan Grundner
 */
public class OrganisationAdminViewState implements ViewState<OrganisationAdminForm> {

    private final Map<String, OrganisationAdminForm> forms = new LinkedHashMap<>();

    private OrganisationAdminForm currentForm;

    @Override
    public Map<String, OrganisationAdminForm> getForms() {
        return forms;
    }

    public OrganisationAdminForm getCurrentForm() {
        return currentForm;
    }

    public void setCurrentForm(OrganisationAdminForm currentForm) {
        this.currentForm = currentForm;
    }

    public OrganisationAdminForm getForm(String id) {
        return forms.get(id);
    }

    public OrganisationAdminForm putForm(OrganisationAdminForm form) {
        return forms.put(form.getId(), form);
    }
}
