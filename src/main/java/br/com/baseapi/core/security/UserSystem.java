package br.com.baseapi.core.security;

import br.com.baseapi.domain.model.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserSystem extends org.springframework.security.core.userdetails.User {
    private final User user;

    public UserSystem(User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getEmail(), new String(user.getPassword()), authorities);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
