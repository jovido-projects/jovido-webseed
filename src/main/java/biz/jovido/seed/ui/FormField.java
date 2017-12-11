package biz.jovido.seed.ui;

/**
 * @author Stephan Grundner
 */
public class FormField<T> extends Field<T> {

    private Form form;

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }
}
