package biz.jovido.seed.hostname;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@Service
public class DomainService {

    private final DomainRepository domainRepository;

    public List<Domain> getDomains() {
        return domainRepository.findAll();
    }

    public Domain getDomain(String name) {
        return domainRepository.findByName(name);
    }

    public Domain getDomain(HttpServletRequest request) {
        return getDomain(request.getServerName());
    }

    @Transactional
    public Domain getOrCreateDomain(String name) {
        Domain domain = domainRepository.findByName(name);
        if (domain == null) {
            domain = new Domain();
            domain.setName(name);
            domain = domainRepository.save(domain);
        }

        return domain;
    }

    public DomainService(DomainRepository domainRepository) {
        this.domainRepository = domainRepository;
    }
}
