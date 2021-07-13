package br.com.baseapi.service;

import br.com.baseapi.domain.User;

import java.util.Optional;

public interface UserService {
    void sendWelcomeEmail(User user);
    Optional<User> findByEmail(String email);
}
