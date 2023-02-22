package ru.nidecker.relexTestTask.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nidecker.relexTestTask.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

}
