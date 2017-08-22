package biz.jovido.seed.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * @author Stephan Grundner
 */
public class PropertyUtils {

    public static Object getPropertyValue(Object bean, String propertyPath) {
        BeanWrapper wrapper = new BeanWrapperImpl(bean);
        return wrapper.getPropertyValue(propertyPath);
    }
}
