package ru.nidecker.relexTestTask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nidecker.relexTestTask.entity.Wallet;

import java.util.List;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    List<Wallet> findAllByUserId(Long userId);

    Optional<Wallet> findByNameAndUserId(String name, Long userId);
}
