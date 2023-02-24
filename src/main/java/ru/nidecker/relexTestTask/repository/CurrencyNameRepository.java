package ru.nidecker.relexTestTask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nidecker.relexTestTask.entity.CurrencyName;

public interface CurrencyNameRepository extends JpaRepository<CurrencyName, Long> {

}
