package biz.jovido.seed.security;

import org.springframework.stereotype.Service;

/**
 * @author Stephan Grundner
 */
@Service
public class SecurityService {

    public boolean isAuthenticated() {
        return true;
    }
}
