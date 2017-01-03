package biz.jovido.seed.content.service;

import biz.jovido.seed.content.repository.FragmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Stephan Grundner
 */
@Service
public class FragmentService {

    private final FragmentRepository fragmentRepository;

    @Autowired
    public FragmentService(FragmentRepository fragmentRepository) {
        this.fragmentRepository = fragmentRepository;
    }

//    public boolean isFragment(Class<?> clazz) {
//        return FragmentUtils.isFragment(clazz);
//    }
//    public boolean isFragment(Object object) {
//        return FragmentUtils.isFragment(object);
//    }
//
//    public <F extends Fragment> Collection<Attribute<F, ?>> getAttributes(Class<F> clazz) {
//        FragmentType<F> fragmentType = FragmentUtils.fragmentType(clazz);
//        return fragmentType.getAttributes();
//    }
//
//    public <F extends Fragment> Attribute<F, ?> getAttribute(Class<F> clazz, String name) {
//        FragmentType<F> fragmentType = FragmentUtils.fragmentType(clazz);
//        return fragmentType.getAttribute(name);
//    }
}
