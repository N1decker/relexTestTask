package ru.nidecker.relexTestTask.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.nidecker.relexTestTask.AbstractTest;
import ru.nidecker.relexTestTask.dto.RegistrationDTO;
import ru.nidecker.relexTestTask.exception.FieldAlreadyTakenException;
import ru.nidecker.relexTestTask.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


class UserServiceTest extends AbstractTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bcryptPasswordEncoder;

    @InjectMocks
    private UserService userService;


    @Test
    void createUser_shouldThrowException_whenEmailAlreadyExists() {
        final RegistrationDTO dto = mock(RegistrationDTO.class);
        when(userRepository.countUsersByNameEquals(dto.getName())).thenReturn(1);

        assertThrows(FieldAlreadyTakenException.class, () -> userService.createUser(dto));
    }

    @Test
    void createUser_shouldCreateUser_whenDtoIsOk() {
        final RegistrationDTO dto = new RegistrationDTO("test", "test@gmail.com");

        when(userRepository.countUsersByNameEquals(dto.getName())).thenReturn(0);
        when(userRepository.countUsersByEmailEqualsIgnoreCase(dto.getEmail())).thenReturn(0);

        assertNotNull(userService.createUser(dto));

        verify(userRepository, Mockito.times(1)).countUsersByEmailEqualsIgnoreCase(dto.getEmail());
        verify(userRepository, Mockito.times(1)).countUsersByNameEquals(dto.getName());
    }

}