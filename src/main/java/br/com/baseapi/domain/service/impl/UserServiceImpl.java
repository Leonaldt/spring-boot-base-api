package br.com.baseapi.domain.service.impl;

import br.com.baseapi.api.assembler.UserModelAssembler;
import br.com.baseapi.api.model.UserModel;
import br.com.baseapi.api.model.input.UserInput;
import br.com.baseapi.core.mail.Mailer;
import br.com.baseapi.domain.model.User;
import br.com.baseapi.domain.repository.UserRepository;
import br.com.baseapi.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Override
    public UserModel create(UserInput dto) {
        String passwordEncoded = new BCryptPasswordEncoder().encode(dto.getPassword());

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoded);

        this.userRepository.save(user);

        return this.userModelAssembler.toModel(user);
    }

    @Override
    public List<UserModel> findAll() {
        return this.userModelAssembler.toCollectionModel(this.userRepository.findAll());
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public void sendWelcomeEmail(User user) {
        String message = this.constructEmail(user);
        this.mailer.send(this.mailUsername, user.getEmail(), "Welcome", message);
    }

    private String constructEmail(User user) {
        String doubleBrokenLine = "</br></br>";

        return "Olá, " + user.getName() + "." + doubleBrokenLine +
                "Clique no link para redefinir sua senha";
    }
}
