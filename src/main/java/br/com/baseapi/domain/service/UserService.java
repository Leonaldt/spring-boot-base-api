package br.com.baseapi.domain.service;

import br.com.baseapi.api.model.UserModel;
import br.com.baseapi.api.model.input.UserInput;
import br.com.baseapi.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserModel create(UserInput userInput);
    List<UserModel> findAll();
    Optional<User> findByEmail(String email);
    void sendWelcomeEmail(User user);
}
