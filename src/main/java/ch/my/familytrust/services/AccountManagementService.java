package ch.my.familytrust.services;


import ch.my.familytrust.dtos.AccountCashFlowDto;
import ch.my.familytrust.dtos.AccountResponseDto;
import ch.my.familytrust.entities.Account;
import ch.my.familytrust.entities.AccountCashFlow;
import ch.my.familytrust.enums.CashflowType;
import ch.my.familytrust.repositories.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public Account findAccountEntityById(UUID accountId){
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

        AccountCashFlow accountCashFlow = new AccountCashFlow(cashFlowAmount, cashflowType, comment);
        account.accountCashFlowTransaction(accountCashFlow);
        account.setLastAccess(LocalDateTime.now());
        accountRepository.save(account);
        accountRepository.flush();
        return account;
    }

    @Transactional
    public AccountResponseDto getAccountDtoById(UUID accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found")); // Bessere Exception-Behandlung hier

        return mapToAccountResponseDto(account);
    }

    // ... (Ihre anderen Service-Methoden)

    // Private Hilfsmethode zum Mappen einer Account-Entität zu einem AccountResponseDto
    private AccountResponseDto mapToAccountResponseDto(Account account) {
        // Zuerst die Liste der AccountCashFlow-Entitäten in DTOs mappen
        List<AccountCashFlowDto> cashFlowDtos = account.getAccountCashFlows().stream() // Annahme: getAccountCashFlows() ist der Getter in Ihrer Account-Entität
                .map(this::mapToAccountCashFlowDto) // Verwendet die untenstehende Hilfsmethode
                .collect(Collectors.toList());

        // Dann das Haupt-AccountResponseDto erstellen
        return new AccountResponseDto(
                account.getId(),
                account.getAccountName(),
                account.getCurrencyCode(),
                account.getOwnerUserId(),
                account.getBalance(),
                account.getLastAccess(),
                account.getCreatedDate(),
                account.getAvailableMoney(),
                cashFlowDtos // Hier die gemappte Liste von CashFlow-DTOs übergeben
        );
    }

    // Private Hilfsmethode zum Mappen einer AccountCashFlow-Entität zu einem AccountCashFlowDto
    private AccountCashFlowDto mapToAccountCashFlowDto(AccountCashFlow cashFlow) {
        return new AccountCashFlowDto(
                cashFlow.getUuid(),
                cashFlow.getAccount().getAccountName(),
                cashFlow.getAccount().getCurrencyCode(),
                cashFlow.getCashFlowAmount(),
                cashFlow.getAccount().getOwnerUserId(),
                cashFlow.getCashFlowType(),
                cashFlow.getComment(),
                cashFlow.getCashFlowDate()
        );
    }

}
