package br.com.baseapi.security;

import br.com.baseapi.domain.User;
import br.com.baseapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class AppUserDetailsService implements UserDetailsService {

     @Autowired
     private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha inválidos"));

        return new UserSystem(user, getPermissions(user));
    }

    private Collection<? extends GrantedAuthority> getPermissions(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        // user.getProfile().getProfilePermissions().forEach(profilePermission -> {
            // authorities.add(new SimpleGrantedAuthority(profilePermission.getPermission().getDescription()));
        // });
        return authorities;
    }

}
