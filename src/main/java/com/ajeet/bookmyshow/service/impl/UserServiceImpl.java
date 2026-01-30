package com.ajeet.bookmyshow.service.impl;

import com.ajeet.bookmyshow.model.User;
import com.ajeet.bookmyshow.repository.UserRepository;
import com.ajeet.bookmyshow.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> listAll() {
        return userRepository.findAll();
    }
}
