package ru.otus.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.library.repository.UserRepository;
import ru.otus.library.domain.User;
import ru.otus.library.service.exception.UserNotExistsException;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(s);
        if (user == null) {
            throw new UserNotExistsException("There is no user with login = " + s);
        }
        return user;
    }
}
