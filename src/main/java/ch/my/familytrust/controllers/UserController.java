package ch.my.familytrust.controllers;


import ch.my.familytrust.dtos.AccountCashFlowRequest;
import ch.my.familytrust.dtos.CreateAccountRequest;
import ch.my.familytrust.entities.Account;
import ch.my.familytrust.services.AccountManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.util.List;
import java.util.UUID;

@RestController()
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    AccountManagementService accountManagementService;

    public UserController(AccountManagementService accountManagementService) {
        this.accountManagementService = accountManagementService;
    }


    @GetMapping("/user/{user-id}/get/accounts")
    public ResponseEntity<Object> getUsers(@PathVariable("user-id") UUID userId){
        System.out.println(userId);
        List<Account> accounts = accountManagementService.getAccountsByUserId(userId);
        System.out.println(accounts.isEmpty());
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    /**
     * @param json-request
     *
     * */
    @PostMapping("/user/{user-id}/create/account")
    public ResponseEntity<Object> createUser(@RequestBody CreateAccountRequest request, @PathVariable("user-id") UUID userId){
        Account account = accountManagementService.createAccount(new Account(request.currencyCode(), request.accountName(), request.ownerUserId()));
        accountManagementService.createAccountEntity(request.currencyCode(), request.accountName(), request.ownerUserId());
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    /**
     * @param json-request
     *
     * */
    @PostMapping("/user/{user-id}/account/{account-id}/cashFlowTransaction")
    public ResponseEntity<Object> depositMoney(@RequestBody AccountCashFlowRequest request){
        Account account = accountManagementService.makeAccountCashFlowTransaction(request.cashFlowDate(), request.userId(), request.accountId(), request.cashFlowAmount(), request.comment());
        return new ResponseEntity<>("TRANSACTION SUCCESSFULL ACCOUNT CASHFLOW AMOUNT : " +account.getAvailableMoney().toString(), HttpStatus.OK);
    }

    @DeleteMapping("/user/{user-id}/delete/account/{account-id}")
    public ResponseEntity<Object> deleteUser(){
        return null;
    }

    /**
     * @param json-request
     *
     * */
    @PatchMapping("/user/{user-id}/change/account")
    public ResponseEntity<Object> changeUser(){
        return null;
    }




}
