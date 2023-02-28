package ru.nidecker.relexTestTask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.nidecker.relexTestTask.entity.Wallet;

import java.util.List;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    List<Wallet> findAllByUserId(Long userId);

    Optional<Wallet> findByNameAndUserId(String name, Long userId);

    @Query(value = "select sum(balance) from wallet where name = :walletName", nativeQuery = true)
    double getSumOfCurrenciesByWalletName(String walletName);
}
