package ch.my.familytrust.controller;

import ch.my.familytrust.entities.Stock;
import ch.my.familytrust.entities.Transaction;
import ch.my.familytrust.enums.INVESTMENT_TYPE;
import ch.my.familytrust.enums.TRANSACTION_TYPE;
import ch.my.familytrust.services.TransactionService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.PostUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Transaction API", description = "API für alle Transaktionen")
@RestController
@RequestMapping(path="api/v1/family-trust")
public class TransactionController {

    TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    @Operation(summary = "Alle Transaktionen abrufen")
    @GetMapping("/getAllActiveTransactions")
    public List<Transaction> test1(){
        return transactionService.getTransactions();
    }


    @Operation(summary = "Erlaubt es eine Transaktion zu anzusetzen.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found the foo", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Transaction.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Foo not found", content = @Content) })

    @PostMapping("/createTransaction")
    public void test2(@RequestBody Transaction transaction){
        transaction.getInvestmentType();
        System.out.println(transaction.toString());
        transaction.setTransactionType(TRANSACTION_TYPE.BUY);
        transactionService.createNewTransaction(transaction);
    }
    @Operation(summary = "Alle Transaktionen abrufen")
    @PutMapping
    public void test3(){

    }
    @Operation(summary = "Alle Transaktionen abrufen")
    @DeleteMapping
    public void test4(){

    }
    @Operation(summary = "Alle Transaktionen abrufen")
    @GetMapping("/getStocksOf/{owner}")
    public List<Transaction> test5(@PathVariable String owner){
        System.out.println(owner);
        return transactionService.getTransactionsOfOwner(owner);
    }

}
