package ru.nidecker.relexTestTask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.nidecker.relexTestTask.entity.WalletAudit;

import java.time.LocalDate;

public interface WalletAuditRepository extends JpaRepository<WalletAudit, Long> {

   @Query("select count(*) from WalletAudit w where w.transactionDate between :dateFrom and :dateTo")
    Long countTransactions(LocalDate dateFrom, LocalDate dateTo);
}
