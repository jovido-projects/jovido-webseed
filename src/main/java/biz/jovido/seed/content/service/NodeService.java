package biz.jovido.seed.content.service;

import biz.jovido.seed.content.model.Fragment;
import biz.jovido.seed.content.repository.FragmentRepository;
import biz.jovido.seed.content.repository.NodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Stephan Grundner
 */
@Service
public class NodeService {

    private final FragmentRepository fragmentRepository;
    private final NodeRepository nodeRepository;
    private final Set<FragmentHandler> fragmentHandlers = new LinkedHashSet<>();

    @Autowired
    public NodeService(FragmentRepository fragmentRepository, NodeRepository nodeRepository) {
        this.fragmentRepository = fragmentRepository;
        this.nodeRepository = nodeRepository;

        registerFragmentHandler(new GenericFragmentHandler());
    }

    public Fragment getFragment(Long id) {
        return fragmentRepository.findOne(id);
    }

    public Fragment saveFragment(Fragment fragment) {
        return fragmentRepository.save(fragment);
    }

    public boolean registerFragmentHandler(FragmentHandler fragmentHandler) {
        return fragmentHandlers.add(fragmentHandler);
    }

    public boolean removeFragmentHandler(FragmentHandler fragmentHandler) {
        return fragmentHandlers.remove(fragmentHandler);
    }

    public FragmentHandler getFragmentHandler(Class<? extends Fragment> fragmentType) {
        for (FragmentHandler fragmentHandler : fragmentHandlers) {
            if (fragmentHandler.supportsType(fragmentType)) {
                return fragmentHandler;
            }
        }

        return null;
    }
}
