package biz.jovido.seed.content.service;

import biz.jovido.seed.content.component.ControlFactory;
import biz.jovido.seed.content.model.Fragment;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@Component("fragmentHandler")
public class GenericFragmentHandler implements FragmentHandler {

    private final ControlFactory fieldFactory;

    @Override
    public boolean supportsType(Class<? extends Fragment> fragmentType) {
        return Fragment.class.isAssignableFrom(fragmentType);
    }

    @Override
    public Object getComponent(Fragment fragment, String propertyName, String nestedPath) {
//        Field field = ReflectionUtils.findField(fragment.getClass(), propertyName);
//        Class fieldType = field.getType();
////        PropertyAccessorUtils
//
//        if (fieldType.isPrimitive() || String.class.isAssignableFrom(fieldType)) {
//            return fieldFactory.getControl(fragment.getClass(), propertyName, "fragment");
//        } else if (Date.class.isAssignableFrom(fieldType)) {
//            return fieldFactory.getControl(fragment.getClass(), propertyName, "fragment");
//        } else if (AnnotationUtils.findAnnotation(field, Embedded.class) != null) {
//            return fieldFactory.getControl(fragment.getClass(), propertyName, "fragment");
//        } else {
//            if (ContentUtils.isCollectionField(field)) {
//                ControlList inputList = new ControlList("fragment");
//                inputList.setPropertyName(propertyName);
//                try {
//                    Collection collection = (Collection) field.get(fragment);
//                    for (int i = 0; i < collection.size(); i++) {
//                        Control input = fieldFactory.getControl(Fragment.class, propertyName, "fragment");
//                        input.setIndex(i);
//                        inputList.addControl(input);
//                    }
//                } catch (IllegalAccessException e) {
//                    throw new RuntimeException(e);
//                }
//
//                return inputList;
//            }
//        }
//
//        return null;
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addValue(Fragment fragment, String fieldName, Object value) {
        BeanWrapper wrapper = new BeanWrapperImpl(fragment);
        @SuppressWarnings("unchecked")
        Collection<Object> collection = (Collection<Object>)
                wrapper.getPropertyValue(fieldName);
        return collection.add(value);
    }

    @Override
    public Object removeValue(Fragment fragment, String fieldName, int index) {
        BeanWrapper wrapper = new BeanWrapperImpl(fragment);
        @SuppressWarnings("unchecked")
        List<Object> collection = (List<Object>)
                wrapper.getPropertyValue(fieldName);
        return collection.remove(index);
    }

    public GenericFragmentHandler(ControlFactory fieldFactory) {
        this.fieldFactory = fieldFactory;
    }
}
