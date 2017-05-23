package biz.jovido.seed;

import org.springframework.stereotype.Service;

/**
 * @author Stephan Grundner
 */
@Service
public class OrganisationService {

    private final OrganisationRepository organisationRepository;

    public OrganisationService(OrganisationRepository organisationRepository) {
        this.organisationRepository = organisationRepository;
    }
}
