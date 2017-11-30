package biz.jovido.seed.ui.source;

/**
 * @author Stephan Grundner
 */
public class BeanSourceProperty implements SourceProperty {

    private final BeanSource source;
    private final String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getValue() {
        return source.wrapper.getPropertyValue(name);
    }

    @Override
    public void setValue(Object value) {
        source.wrapper.setPropertyValue(name, value);
    }

    public BeanSourceProperty(BeanSource source, String name) {
        this.source = source;
        this.name = name;
    }
}
