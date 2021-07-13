package br.com.baseapi.service.impl;

import br.com.baseapi.domain.User;
import br.com.baseapi.mail.Mailer;
import br.com.baseapi.repository.UserRepository;
import br.com.baseapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Value("${mail.username}")
    private String mailUsername;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Mailer mailer;

    @Override
    public void sendWelcomeEmail(User user) {
        String message = this.constructEmail(user);
        this.mailer.send(this.mailUsername, user.getEmail(), "Welcome", message);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    private String constructEmail(User user) {
        String doubleBrokenLine = "</br></br>";

        return "Olá, " + user.getName() + "." + doubleBrokenLine +
                "Clique no link para redefinir sua senha";
    }
}
