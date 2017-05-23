package biz.jovido.seed;

import java.util.UUID;

/**
 * @author Stephan Grundner
 */
public class OrganisationAdminForm implements Form {

    private final String id = UUID.randomUUID().toString();

    @Override
    public String getId() {
        return id;
    }
}
