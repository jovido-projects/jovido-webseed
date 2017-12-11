package biz.jovido.seed.ui;

/**
 * @author Stephan Grundner
 */
public final class FieldUtils {

    @SuppressWarnings("unchecked")
    public static <T> void bind(Field<T> field, Binding binding, String propertyName) {
        BindingProperty<T> property = (BindingProperty<T>)
                binding.getProperty(propertyName);
        field.setProperty(property);
    }

    private FieldUtils() {}
}
