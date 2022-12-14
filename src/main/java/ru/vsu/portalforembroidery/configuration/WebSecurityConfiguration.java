package ru.vsu.portalforembroidery.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.vsu.portalforembroidery.mapper.UserMapper;
import ru.vsu.portalforembroidery.repository.UserRepository;
import ru.vsu.portalforembroidery.service.UserDetailsServiceImpl;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsServiceBean())
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    @Bean
    public UserDetailsService userDetailsServiceBean() {
        return new UserDetailsServiceImpl(userRepository, userMapper);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .anyRequest().permitAll();
        return httpSecurity.build();
    }

}
