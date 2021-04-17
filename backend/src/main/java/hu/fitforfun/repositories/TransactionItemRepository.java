package hu.fitforfun.repositories;

import hu.fitforfun.model.shop.TransactionItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionItemRepository extends JpaRepository<TransactionItem, Long> {
}
