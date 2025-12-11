package ru.iarmoshenko.NauJava.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.iarmoshenko.NauJava.entity.User;
import ru.iarmoshenko.NauJava.repository.UserRepository;

import java.util.List;

/**
 * Тесты для проверки функционала UserDetailsService.
 */
@SpringBootTest
public class UserDetailsTest {
    private UserRepository userRepository;
    private UserDetailsService userDetailsService;

    @BeforeEach
    public void setup() {
        userRepository = Mockito.mock(UserRepository.class);
        userDetailsService = new UserDetailsServiceImpl(userRepository);
    }

    @Test
    public void testLoadUserByUsername() {
        var user = new User("username", "email", "hash");
        var authorityExpected = new SimpleGrantedAuthority("ROLE_USER");
        Mockito.when(userRepository.findByUsernameOrEmail("username", null))
                .thenReturn(List.of(user));

        var userDetails = userDetailsService.loadUserByUsername("username");

        Assertions.assertEquals(user.getUsername(), userDetails.getUsername());
        Assertions.assertTrue(userDetails.getAuthorities().contains(authorityExpected));
    }

    @Test
    public void testLoadUserByUsernameNotFoundException() {
        Mockito.when(userRepository.findByUsernameOrEmail("notExist", null))
                .thenReturn(List.of());

        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("notExist"));
    }

}
