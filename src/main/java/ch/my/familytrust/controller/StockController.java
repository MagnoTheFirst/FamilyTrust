package ch.my.familytrust.controller;

import ch.my.familytrust.entities.Stock;
import ch.my.familytrust.entities.Transaction;
import ch.my.familytrust.enums.INVESTMENT_TYPE;
import ch.my.familytrust.enums.TRANSACTION_TYPE;
import ch.my.familytrust.services.StockService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.PostUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Stock API", description = "API für alle Assets")
@RestController
//@RequestMapping(path="api/v1/family-trust")stocks
@RequestMapping(path="/stocks")
public class StockController {

    StockService transactionService;

    @Autowired
    public StockController(StockService transactionService) {
        this.transactionService = transactionService;
    }
    @Operation(summary = "Alle Investitionen abrufen")
    @GetMapping("/getAllActiveStocks")
    public List<Stock> test1(){
        return transactionService.getTransactions();
    }


    @Operation(summary = "Erlaubt es eine Investition zu dokumentieren.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found the foo", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Transaction.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Foo not found", content = @Content) })

    @PostMapping("/buy")
    public void test2(@ModelAttribute Stock transaction){
        transaction.getInvestmentType();
        System.out.println(transaction.toString());
        transaction.setTransactionType(TRANSACTION_TYPE.BUY);
        transactionService.createNewTransaction(transaction);
    }


    @Operation(summary = "Alle Stocks abrufen")
    @PutMapping
    public void test3(){

    }
    @Operation(summary = "Alle Transaktionen abrufen")
    @DeleteMapping("/removeStock/{stockId}")
    public void test4(@PathVariable UUID stockId){
        transactionService.cancelTransaction(stockId);
    }

    @Operation(summary = "Alle Stocks eines Nuetzers abrufen")
    @GetMapping("/getStocksOf/{owner}")
    public List<Stock> test5(@PathVariable String owner){
        System.out.println(owner);
        return transactionService.getTransactionsOfOwner(owner);
    }


    @Operation(summary = "Alle Stocks eines Nuetzers abrufen")
    @GetMapping("/getStocks")
    public List<Stock> test7(){
        return transactionService.getTransactions();
    }

    @GetMapping
    public String listStocks(Model model) {
        model.addAttribute("stocks", transactionService.getTransactions());
        return "stocks.html";
    }

    @GetMapping("/new")
    public String newStock(Model model) {
        model.addAttribute("stock", new Stock());
        return "stock-form";
    }

    @PostMapping("/save")
    public String saveStock(@ModelAttribute Stock stock) {
        transactionService.createNewTransaction(stock);
        return "redirect:/stocks";
    }

    @GetMapping("/delete/{id}")
    public String deleteStock(@PathVariable UUID id) {
        transactionService.cancelTransaction(id);
        return "redirect:/stocks";
    }
}
