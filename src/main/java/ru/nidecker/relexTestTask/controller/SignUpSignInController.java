package ru.nidecker.relexTestTask.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.nidecker.relexTestTask.dto.RegistrationDTO;
import ru.nidecker.relexTestTask.service.UserService;

@RestController
@RequestMapping(path = "rest")
@RequiredArgsConstructor
@Slf4j
public class SignUpSignInController {

    private final UserService userService;

    @PostMapping("/registration")
    public String registrationUser(@RequestBody RegistrationDTO dto) {
        log.info("attempt to registration by user {}", dto);
        return userService.createUser(dto);
    }
}
