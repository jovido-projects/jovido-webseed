package biz.jovido.seed.security.service;

import biz.jovido.seed.security.model.Role;
import biz.jovido.seed.security.model.RoleRepository;
import biz.jovido.seed.security.model.User;
import biz.jovido.seed.security.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * @author Stephan Grundner
 */
@Service
public class SecurityService implements UserDetailsService, AuditorAware<User> {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof User) {
            return (User) principal;
        }

        return null;
    }

    public boolean isAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
                //when Anonymous Authentication is enabled
                !(SecurityContextHolder.getContext().getAuthentication()
                        instanceof AnonymousAuthenticationToken);
    }

    public User getUser(Long id) {
        return userRepository.findOne(id);
    }

    public User findUser(String name) {
        return userRepository.findByName(name);
    }

    public User findOrCreateUser(String name) {
        User user = findUser(name);
        if (user == null) {
            user = new User();
            user.setName(name);
        }

        return user;
    }

    public User createUser() {
        return new User();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByName(username);
    }

    public User saveUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    public Page<User> findAllUsers(int offset, int max) {
        return userRepository.findAll(new PageRequest(offset, max));
    }

    public Role findRole(String authority) {
        return roleRepository.findByAuthority(authority);
    }

    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    public Role findOrCreateRole(String authority) {
        Role role = findRole(authority);
        if (role == null) {
            role = new Role();
            role.setAuthority(authority);
        }

        return role;
    }

    public Role saveRole(Role role) {
        return roleRepository.saveAndFlush(role);
    }

    @Override
    public User getCurrentAuditor() {
        return getCurrentUser();
    }
}
