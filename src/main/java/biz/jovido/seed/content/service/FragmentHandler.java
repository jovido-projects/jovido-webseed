package biz.jovido.seed.content.service;

import biz.jovido.seed.content.model.Fragment;

/**
 * @author Stephan Grundner
 */
public interface FragmentHandler {

    boolean supportsType(Class<? extends Fragment> fragmentType);

    Object getComponent(Fragment fragment, String propertyName, String nestedPath);

    boolean addValue(Fragment fragment, String fieldName, Object value);
    Object removeValue(Fragment fragment, String fieldName, int index);
}