package biz.jovido.seed.content.service;

import biz.jovido.seed.content.metamodel.Attribute;
import biz.jovido.seed.content.metamodel.FragmentType;
import biz.jovido.seed.content.model.Fragment;
import biz.jovido.seed.content.repository.FragmentRepository;
import biz.jovido.seed.content.util.FragmentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

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

    public <F extends Fragment> Collection<Attribute<F, ?>> getAttributes(Class<F> clazz) {
        FragmentType<F> fragmentType = FragmentUtils.fragmentType(clazz);
        return fragmentType.getAttributes();
    }

    public <F extends Fragment> Attribute<F, ?> getAttribute(Class<F> clazz, String name) {
        FragmentType<F> fragmentType = FragmentUtils.fragmentType(clazz);
        return fragmentType.getAttribute(name);
    }
}
