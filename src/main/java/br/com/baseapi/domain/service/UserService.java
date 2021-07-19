package br.com.baseapi.domain.service;

import br.com.baseapi.api.model.UserModel;
import br.com.baseapi.api.model.input.UserInput;
import br.com.baseapi.domain.model.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserModel create(UserInput input);
    UserModel update(UserInput input, Long id);
    List<UserModel> findAll();
    User findById(Long id);
    Optional<User> findByEmail(String email);
    void delete(Long id);
    void sendWelcomeEmail(User user);
    Page<UserModel> page(Integer page, Integer linesPerPage, String orderBy, String direction, String parameter);
}
