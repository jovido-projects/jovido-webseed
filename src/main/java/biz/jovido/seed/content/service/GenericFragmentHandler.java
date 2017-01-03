package biz.jovido.seed.content.service;

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

    @Override
    public boolean supportsType(Class<? extends Fragment> fragmentType) {
        return Fragment.class.isAssignableFrom(fragmentType);
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

    @Override
    public boolean movePropertyUp(Fragment fragment, String fieldName, int index) {
        BeanWrapper wrapper = new BeanWrapperImpl(fragment);
        @SuppressWarnings("unchecked")
        List<Object> list = (List<Object>)
                wrapper.getPropertyValue(fieldName);

        Object element = list.get(index);
        list.set(index, list.get(index - 1));
        list.set(index - 1, element);

        return true;
    }
}
