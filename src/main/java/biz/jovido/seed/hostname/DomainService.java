package biz.jovido.seed.hostname;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Stephan Grundner
 */
@Service
public class DomainService {

    private final DomainRepository domainRepository;

    public Domain getDomain(String name) {
        return domainRepository.findByName(name);
    }

    public Domain getDomain(HttpServletRequest request) {
        return getDomain(request.getRemoteHost());
    }

    public DomainService(DomainRepository domainRepository) {
        this.domainRepository = domainRepository;
    }
}
