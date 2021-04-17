package hu.fitforfun.controller;

import hu.fitforfun.exception.FitforfunException;
import hu.fitforfun.exception.Response;
import hu.fitforfun.model.shop.Transaction;
import hu.fitforfun.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping(value = "/purchase", consumes = MediaType.APPLICATION_JSON_VALUE,
    produces =  MediaType.APPLICATION_JSON_VALUE)
    public Response purchase(@RequestBody Transaction transaction) {
        try {
            System.err.println("ma" + transaction);
            return Response.createOKResponse(transactionService.createTransaction(transaction));
        } catch (FitforfunException e) {
            return Response.createErrorResponse(e);
        }
    }

    @GetMapping("")
    public List<Transaction> getAll() {
        return transactionService.listTransactions(0, 10);
    }
}
