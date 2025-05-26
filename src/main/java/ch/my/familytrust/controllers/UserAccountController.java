package ch.my.familytrust.controllers;

import ch.my.familytrust.dtos.AccountCashFlowRequest;
import ch.my.familytrust.dtos.AccountResponseDto;
import ch.my.familytrust.dtos.CreateAccountRequest;
import ch.my.familytrust.dtos.DeleteAccountRequest;
import ch.my.familytrust.entities.Account;
import ch.my.familytrust.enums.CashflowType;
import ch.my.familytrust.services.AccountManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController("api/v1")
public class UserAccountController {



    @Autowired
    AccountManagementService accountManagementService;

    public UserAccountController(AccountManagementService accountManagementService) {
        this.accountManagementService = accountManagementService;
    }


    @GetMapping("/user/{user-id}/get/accounts")
    public ResponseEntity<Object> getUsers(@PathVariable("user-id") UUID userId){
        System.out.println(userId);
        List<Account> accounts = accountManagementService.getAccountsByUserId(userId);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }



    @GetMapping("/user/{user-id}/get/accounts1")
    public ResponseEntity<Object> getUsers1(@PathVariable("user-id") UUID userId){

        return null;
    }


    /**
     * @param json-request
     *
     * */
    @PostMapping("/user/create/account")
    public ResponseEntity<Object> createUser(@RequestBody CreateAccountRequest request, @PathVariable("user-id") UUID userId){
        Account account = accountManagementService.createAccount(new Account(request.currencyCode(), request.accountName(), request.ownerUserId()));
        accountManagementService.createAccountEntity(request.currencyCode(), request.accountName(), request.ownerUserId());
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    /**
     * @param json-request
     *
     * */
    @PostMapping("/user/account/cashFlowTransaction")
    public ResponseEntity<Object> accountTransaction(@RequestBody AccountCashFlowRequest request){

        if(request.cashFlowType().equals(CashflowType.DEPOSIT)){
            Account account = accountManagementService.makeAccountCashFlowTransaction(request.cashFlowDate(), request.userId(), request.cashFlowType(), request.accountId(), request.cashFlowAmount(), request.comment());
            return new ResponseEntity<>("TRANSACTION SUCCESSFULL ACCOUNT CASHFLOW AMOUNT : " +account.getAvailableMoney().toString(), HttpStatus.OK);
        } else if (request.cashFlowType().equals(CashflowType.WITHDRAWAL)) {
            Account account = accountManagementService.makeAccountCashFlowTransaction(request.cashFlowDate(), request.userId(),  request.cashFlowType(),request.accountId(), request.cashFlowAmount(), request.comment());
            System.out.println(account.getAvailableMoney());
            return new ResponseEntity<>("TRANSACTION SUCCESSFULL ACCOUNT WITHDRAWAL AMOUNT : " +account.getAvailableMoney().toString(), HttpStatus.OK);
        }
        else if(request.cashFlowType().equals(CashflowType.DIVIDEND_PAYMENT)){
            Account account = accountManagementService.makeAccountCashFlowTransaction(request.cashFlowDate(), request.userId(), request.cashFlowType(), request.accountId(), request.cashFlowAmount(), request.comment());
            return new ResponseEntity<>("TRANSACTION SUCCESSFULL ACCOUNT DIVIDEND PAYMENT AMOUNT : " +account.getAvailableMoney().toString(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("TRANSACTION FAILED", HttpStatus.BAD_REQUEST);
        }
    }



    @DeleteMapping("/user/{user-id}/delete/account/{account-id}")
    public ResponseEntity<Object> deleteUserAccount(@RequestBody DeleteAccountRequest request){
        Account account = accountManagementService.getAccountByAccountId(request.accountId());
        if(account.getOwnerUserId().equals(request.userId())){
            accountManagementService.deleteAccount(account);
            return new ResponseEntity<>("ACCOUNT DELETED", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("ACCOUNT NOT DELETED", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param json-request
     *
     * */
    @PatchMapping("/user/{user-id}/change/account")
    public ResponseEntity<Object> changeUser(){
        return null;
    }


    /**
     * @param json-request
     *
     * */
    @GetMapping("/user/{user-id}/get/account/{account-id}")
    public ResponseEntity<Object> getAccount(@PathVariable("user-id") UUID userId, @PathVariable("account-id") UUID accountId){
        AccountResponseDto account = accountManagementService.getAccountDtoById(accountId);
        if (!account.ownerUserId().equals(userId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(account, HttpStatus.OK);
    }


}
