package ru.nidecker.relexTestTask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nidecker.relexTestTask.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
