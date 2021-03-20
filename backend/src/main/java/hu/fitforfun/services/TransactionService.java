package hu.fitforfun.services;

import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.shop.Transaction;
import hu.fitforfun.model.user.User;

import java.util.List;

public interface TransactionService {
    Transaction getTransactionById(Long id) throws FitforfunException;

    List<Transaction> listTransactions(int page, int limit);

    List<Transaction> listTransactionsByUser(User user);

    Transaction createTransaction(Transaction transaction) throws FitforfunException;

    void deleteTransaction(Long id) throws FitforfunException;

    Transaction updateTransaction(Long id,Transaction transaction) throws FitforfunException;
}
