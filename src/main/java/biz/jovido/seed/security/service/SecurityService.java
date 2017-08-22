package biz.jovido.seed.security.service;

import biz.jovido.seed.security.model.Role;
import biz.jovido.seed.security.model.RoleRepository;
import biz.jovido.seed.security.model.User;
import biz.jovido.seed.security.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
public class SecurityService implements UserDetailsService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public User getUser(Long id) {
        return userRepository.findOne(id);
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

    public Role getOrCreateRole(String authority) {
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
}
