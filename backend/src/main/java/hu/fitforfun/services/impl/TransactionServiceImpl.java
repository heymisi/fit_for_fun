package hu.fitforfun.services.impl;

import hu.fitforfun.exception.ErrorCode;
import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.model.shop.ShopItem;
import hu.fitforfun.model.shop.Transaction;
import hu.fitforfun.model.user.User;
import hu.fitforfun.repositories.TransactionRepository;
import hu.fitforfun.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public Transaction getTransactionById(Long id) throws FitforfunException {

        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        if (!optionalTransaction.isPresent()) {
            throw new FitforfunException(ErrorCode.TRANSACTION_NOT_EXISTS);
        }
        return optionalTransaction.get();
    }

    @Override
    public List<Transaction> listTransactions(int page, int limit) {
        if (page > 0) page--;

        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<Transaction> transactions = transactionRepository.findAll(pageableRequest);
        List<Transaction> returnValue = transactions.getContent();
        return returnValue;
    }

    @Override
    public List<Transaction> listTransactionsByUser(User user) {
        return transactionRepository.findByPurchaser(user);
    }

    @Override
    public Transaction createTransaction(Transaction transaction) throws FitforfunException {
        transaction.getTransactionItems().forEach(transactionItem -> {
          //  transactionItem.setPrice(transactionItem.getShopItem().getPrice().doubleValue() * transactionItem.getQuantity());
        });
        return transactionRepository.save(transaction);
    }

    @Override
    public void deleteTransaction(Long id) throws FitforfunException {

        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        if (!optionalTransaction.isPresent()) {
            throw new FitforfunException(ErrorCode.TRANSACTION_NOT_EXISTS);
        }
        transactionRepository.delete(optionalTransaction.get());
    }

    @Override
    public Transaction updateTransaction(Long id, Transaction transaction) throws FitforfunException {
        return null;
    }
}
