package ch.my.familytrust.controller;

import ch.my.familytrust.entities.Transaction;
import ch.my.familytrust.services.TransactionService;
import jakarta.persistence.PostUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/family-trust")
public class TransactionController {

    TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping()
    public List<Transaction> test1(){
        return transactionService.getTransactions();
    }

    @PostMapping()
    public void test2(@RequestBody Transaction transaction){
        transactionService.createNewTransaction(transaction);
    }

    @PutMapping
    public void test3(){

    }

    @DeleteMapping
    public void test4(){

    }

    @GetMapping("/test")
    public String test5(){
        return "test";
    }

}
