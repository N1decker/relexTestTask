package ru.nidecker.relexTestTask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import ru.nidecker.relexTestTask.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    int countUsersByEmailEqualsIgnoreCase(String email);

    int countUsersByNameEquals(String name);

    Optional<User> findByEmail(String email);
}
