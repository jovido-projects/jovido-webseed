package biz.jovido.seed.security;

import biz.jovido.seed.configuration.AdminProperties;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@Service
public class SecurityService implements UserDetailsService, AuditorAware<User> {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityService.class);

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminProperties adminProperties;

    private SecurityContext getSecurityContext() {
        return SecurityContextHolder.getContext();
    }

    private Authentication getAuthentication() {
        return getSecurityContext().getAuthentication();
    }

    public synchronized boolean isAuthenticated() {
        Authentication authentication = getAuthentication();
        return authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);
    }

    public Role getRole(String name) {
        return roleRepository.findByName(name);
    }

    public Role saveRole(Role role) {
        return roleRepository.saveAndFlush(role);
    }

    public Role getOrCreateRole(String name) {
        Role role = getRole(name);
        if (role == null) {
            role = new Role();
            role.setName(name);
            role = saveRole(role);
        }

        return role;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long id) {
        return userRepository.findOne(id);
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }

    public User getAuthenticatedUser() {
        Authentication authentication = getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof User) {
                return (User) principal;
            }
        }

        return null;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUser(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return user;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User saveUser(User user, String newPassword) {
        if (StringUtils.hasText(newPassword)) {
            newPassword = passwordEncoder.encode(newPassword);
        }
        user.setPassword(newPassword);
        return saveUser(user);
    }

    private void ensureAdminUserAndRoleExists() {
        Role role = getOrCreateRole("ADMINISTRATOR");
        User admin = getUser("admin");
        if (admin == null) {
            admin = new User();
            admin.setUsername("admin");
            admin.addRole(role);
            admin = saveUser(admin);
        }

        String password = adminProperties.getPassword();
        if (StringUtils.isEmpty(password)) {
            password = RandomStringUtils.randomAlphanumeric(8);
            LOG.info("Password for admin is [{}]", password);
        }
        admin.setPassword(passwordEncoder.encode(password));
        saveUser(admin);
    }

    @PostConstruct
    private void init() {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(status -> {
            ensureAdminUserAndRoleExists();
            return null;
        });
    }

    @Override
    public User getCurrentAuditor() {
        return getAuthenticatedUser();
    }
}
