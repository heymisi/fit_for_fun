package hu.fitforfun.repositories;

import hu.fitforfun.model.shop.Transaction;
import hu.fitforfun.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long> {

    List<Transaction> findByPurchaser(User purchaser);

}
