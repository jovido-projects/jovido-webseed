package biz.jovido.seed.system.security.service;

import biz.jovido.seed.system.security.model.User;
import biz.jovido.seed.system.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * @author Stephan Grundner
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<User> getAllUsers(int index, int size) {
        return userRepository.findAll(new PageRequest(index, size));
    }

    public User getUser(Long id) {
        return userRepository.findOne(id);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
