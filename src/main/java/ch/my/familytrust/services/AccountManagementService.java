package ch.my.familytrust.services;


import ch.my.familytrust.entities.Account;
import ch.my.familytrust.entities.AccountCashFlow;
import ch.my.familytrust.enums.CashflowType;
import ch.my.familytrust.repositories.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class AccountManagementService {

    @Autowired
    AccountRepository accountRepository;

    public AccountManagementService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(Account account){
        return accountRepository.save(account);
    }

    public Account updateAccount(Account account){
        return accountRepository.save(account);
    }

    public Account deleteAccount(Account account){
        account.setActive(false);
        account.setArchived(true);
        accountRepository.save(account);
        accountRepository.flush();
        log.info("Saved Account: {}, Owner: {}", account.getId(), account.getOwnerUserId());
        return account;
    }

    public Account createAccountEntity(String currencyCode, String accountName, UUID ownerUserId){
        return new Account(currencyCode, accountName, ownerUserId);
    }

    public Account getAccountById(UUID accountId){
        return accountRepository.findById(accountId).orElse(null);
    }

    public List<Account> getAccountsByUserId(UUID ownerUserId){
        return accountRepository.findByOwnerUserId(ownerUserId);
    }

    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    public Account makeAccountCashFlowTransaction(LocalDateTime cashflowDate, UUID ownerUserId, UUID accountId, BigDecimal cashFlowAmount, String comment){
        if(cashflowDate == null ){
            cashflowDate = LocalDateTime.now();
        }
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found with ID " + accountId));

        AccountCashFlow accountCashFlow = new AccountCashFlow(cashFlowAmount, CashflowType.DEPOSIT, comment);
        account = account.accountCashFlowTransaction(accountCashFlow);
        account.setLastAccess(LocalDateTime.now());
        accountRepository.save(account);
        accountRepository.flush();
        return account;
    }



    public Account makeAccountCashFlowTransaction(LocalDateTime cashflowDate, UUID ownerUserId, CashflowType cashflowType, UUID accountId, BigDecimal cashFlowAmount, String comment){
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found with ID " + accountId));
        if(cashflowDate == null || cashFlowAmount == null || cashFlowAmount.compareTo(BigDecimal.ZERO) < 0 || cashflowType == null){
            cashflowDate = LocalDateTime.now();
        }
        if(cashflowType.equals(CashflowType.WITHDRAWAL)){

            AccountCashFlow accountCashFlow = new AccountCashFlow(cashFlowAmount, cashflowType, comment);
            account = account.accountCashFlowTransaction(accountCashFlow);
            account.setLastAccess(LocalDateTime.now());
            accountRepository.save(account);
            accountRepository.flush();
            return account;
        }

        AccountCashFlow accountCashFlow = new AccountCashFlow(cashFlowAmount, cashflowType, comment);
        account.accountCashFlowTransaction(accountCashFlow);
        account.setLastAccess(LocalDateTime.now());
        accountRepository.save(account);
        accountRepository.flush();
        return account;
    }

}
