package ch.my.familytrust.controller;

import ch.my.familytrust.entities.Stock;
import ch.my.familytrust.entities.Transaction;
import ch.my.familytrust.enums.INVESTMENT_TYPE;
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

    @GetMapping("/getAllActiveTransactions")
    public List<Transaction> test1(){
        return transactionService.getTransactions();
    }

    @PostMapping("/createTransaction")
    public void test2(@RequestBody Transaction transaction){
        transaction.getInvestmentType();
        System.out.println(transaction.toString());
        transactionService.createNewTransaction(transaction);
        if(transaction.getInvestmentType() == INVESTMENT_TYPE.STOCK){

        }
        else if(transaction.getInvestmentType()==INVESTMENT_TYPE.CRYPTOCURRENCY){

        }
    }

    @PutMapping
    public void test3(){

    }

    @DeleteMapping
    public void test4(){

    }

    @GetMapping("/getStocksOf/{owner}")
    public List<Transaction> test5(@PathVariable String owner){
        System.out.println(owner);
        return transactionService.getTransactionsOfOwner(owner);
    }

}
