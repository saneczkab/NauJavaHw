package ru.iarmoshenko.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.iarmoshenko.NauJava.repository.UserRepository;

import java.util.List;


@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository
                .findByUsernameOrEmail(username, null)
                .getFirst();

        if (user == null) {
            throw new UsernameNotFoundException("User not found!");
        }

        var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPasswordHash())
                .authorities(authorities)
                .build();
    }
}