package ru.nidecker.relexTestTask.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.nidecker.relexTestTask.entity.enums.Role;
import ru.nidecker.relexTestTask.service.UserService;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider());

        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html").permitAll()
                .requestMatchers("/rest/registration").permitAll()
                .requestMatchers("rest/admin/**").hasRole(Role.ADMIN.name())
                .requestMatchers("rest/user/").hasRole(Role.USER.name())
                .and().httpBasic();

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return authProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userService;
    }
}