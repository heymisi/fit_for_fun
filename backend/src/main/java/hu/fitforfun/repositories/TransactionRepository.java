package hu.fitforfun.repositories;

import hu.fitforfun.model.shop.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
