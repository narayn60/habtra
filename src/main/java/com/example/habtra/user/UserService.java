package com.example.habtra.user;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("userService")
public class UserService {

    private final UserRepository repository;

    UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User findByUsername(String username) {
        return repository.findByUsername(username);
    }

    public User create(User user) {
        return repository.save(user);
    }

    public User getById(UUID id) {
        return repository.findById(id).orElse(null);
    }
}
