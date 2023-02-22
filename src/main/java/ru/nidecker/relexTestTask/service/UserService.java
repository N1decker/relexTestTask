package ru.nidecker.relexTestTask.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.nidecker.relexTestTask.dto.RegistrationDTO;
import ru.nidecker.relexTestTask.entity.Role;
import ru.nidecker.relexTestTask.entity.User;
import ru.nidecker.relexTestTask.exception.FieldAlreadyTakenException;
import ru.nidecker.relexTestTask.repository.UserRepository;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private static final String USER_NOT_FOUND_MESSAGE = "User with email %s not found";
    private final UserRepository userRepository;

    public String createUser(RegistrationDTO dto) {
        if (userRepository.countUsersByNameEquals(dto.getName()) > 0) {
            throw new FieldAlreadyTakenException(String.format("name %s already taken", dto.getName()));
        }
        if (userRepository.countUsersByEmailEqualsIgnoreCase(dto.getEmail()) > 0) {
            throw new FieldAlreadyTakenException(String.format("email %s already taken", dto.getEmail()));
        }

        String secretKey = UUID.randomUUID().toString();
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setSecretKey(secretKey);
        user.setRoles(Set.of(Role.USER));

        userRepository.save(user);
        return secretKey;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(
                        String.format(USER_NOT_FOUND_MESSAGE, email)
                )
        );
    }
}
