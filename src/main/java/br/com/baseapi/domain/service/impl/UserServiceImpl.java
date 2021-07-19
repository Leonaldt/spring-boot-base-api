package br.com.baseapi.domain.service.impl;

import br.com.baseapi.api.assembler.UserInputDisassembler;
import br.com.baseapi.api.assembler.UserModelAssembler;
import br.com.baseapi.api.model.UserModel;
import br.com.baseapi.api.model.input.UserInput;
import br.com.baseapi.core.mail.Mailer;
import br.com.baseapi.domain.exceptions.UserNotFoundException;
import br.com.baseapi.domain.model.User;
import br.com.baseapi.domain.repository.UserRepository;
import br.com.baseapi.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Value("${mail.username}")
    private String mailUsername;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Mailer mailer;

    @Autowired
    private UserModelAssembler userModelAssembler;

    @Autowired
    private UserInputDisassembler userInputDisassembler;

    @Override
    public UserModel create(UserInput input) {
        String passwordEncoded = new BCryptPasswordEncoder().encode(input.getPassword());

        User user = this.userInputDisassembler.toDomainObject(input);
        user.setPassword(passwordEncoded);

        this.userRepository.save(user);

        return this.userModelAssembler.toModel(user);
    }

    @Override
    public List<UserModel> findAll() {
        return this.userModelAssembler.toCollectionModel(this.userRepository.findAll());
    }

    @Override
    public User findById(Long id) {
        return this.userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public void delete(Long id) {
        this.userRepository.delete(this.findById(id));
    }

    @Override
    public void sendWelcomeEmail(User user) {
        String message = this.constructEmail(user);
        this.mailer.send(this.mailUsername, user.getEmail(), "Welcome", message);
    }

    @Override
    public Page<UserModel> page(Integer page, Integer linesPerPage, String orderBy, String direction, String parameter) {
        Pageable pageable = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Page<User> users = null;

        if (parameter == null) users = this.userRepository.findAll(pageable);
        else users = this.userRepository.findByNameLike(parameter, pageable);

        List<UserModel> userModels = this.userModelAssembler.toCollectionModel(users.getContent());
        Page<UserModel> userModelPage = new PageImpl<>(userModels, pageable, users.getTotalElements());

        return userModelPage;
    }

    private String constructEmail(User user) {
        String doubleBrokenLine = "</br></br>";

        return "Olá, " + user.getName() + "." + doubleBrokenLine +
                "Clique no link para redefinir sua senha";
    }
}
