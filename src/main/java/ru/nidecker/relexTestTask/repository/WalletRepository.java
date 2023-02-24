package ru.nidecker.relexTestTask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nidecker.relexTestTask.entity.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
