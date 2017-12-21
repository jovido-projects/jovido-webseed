package biz.jovido.seed.ui;

/**
 * @author Stephan Grundner
 */
public class FixedBindingPathProvider implements BindingPathProvider {

    private final String bindingPath;

    @Override
    public String getBindingPath() {
        return bindingPath;
    }

    public FixedBindingPathProvider(String bindingPath) {
        this.bindingPath = bindingPath;
    }
}
