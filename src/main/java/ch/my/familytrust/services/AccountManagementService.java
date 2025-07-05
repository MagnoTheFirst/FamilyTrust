package ch.my.familytrust.services;


import ch.my.familytrust.dtos.AccountCashFlowDto;
import ch.my.familytrust.dtos.AccountResponseDto;
import ch.my.familytrust.dtos.AssetDto;
import ch.my.familytrust.entities.Account;
import ch.my.familytrust.entities.AccountCashFlow;
import ch.my.familytrust.entities.Asset;
import ch.my.familytrust.entities.AssetTransaction;
import ch.my.familytrust.enums.CashflowType;
import ch.my.familytrust.repositories.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
        log.info("Saved Account: {}, Owner: {}", "ACCOUNT DELETED", account.getId(), account.getOwnerUserId());
        return account;
    }

    public Account createAccountEntity(String currencyCode, String accountName, UUID ownerUserId){
        return new Account(currencyCode, accountName, ownerUserId);
    }

    public Account findAccountEntityById(UUID accountId){
        return accountRepository.findById(accountId).orElse(null);
    }

    public List<Account> getAccountsByUserId(UUID ownerUserId){
        return accountRepository.findByUserIdAndActiveTrue(ownerUserId);
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
                .orElseThrow(() -> new RuntimeException("Account not found"));

        return mapToAccountResponseDto(account);
    }

    public AccountResponseDto mapToAccountResponseDto(Account account) {
        List<AccountCashFlowDto> cashFlowDtos = account.getAccountCashFlows().stream() // Annahme: getAccountCashFlows() ist der Getter in Ihrer Account-Entität
                .map(this::mapToAccountCashFlowDto) // Verwendet die untenstehende Hilfsmethode
                .collect(Collectors.toList());
        
        // Calculate total balance: available money + total asset value
        BigDecimal totalAssetValue = account.getAssets().stream()
                .map(asset -> asset.getAssetBalance() != null ? asset.getAssetBalance() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalBalance = (account.getAvailableMoney() != null ? account.getAvailableMoney() : BigDecimal.ZERO)
                .add(totalAssetValue);
        
        return new AccountResponseDto(
                account.getId(),
                account.getAccountName(),
                account.getCurrencyCode(),
                account.getOwnerUserId(),
                account.getInvestedMoney(),
                totalBalance, // Use calculated balance instead of stored balance
                account.getLastAccess(),
                account.getCreatedDate(),
                account.getAvailableMoney(),
                cashFlowDtos,
                account.getAssets()// Hier die gemappte Liste von CashFlow-DTOs übergeben
        );
    }


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

    @Transactional
    public Account getAccountByAccountId(UUID accountId) {
        Optional<Account> account = Optional.ofNullable(accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found ACCOUNT MANAGEMENT SERVICE. ")));
        return account.get();
    }

    @Transactional
    public List<Asset> getAssetsFromAccount(Account account){
        return account.getAssets();
    }

    public Optional<Asset> getAssetFromAccountAssetsWithAssetName(List<Asset> assets, String assetName){
        return assets.stream().filter(asset -> asset.getName().equals(assetName)).findFirst();
    }

    public Optional<Asset> getAssetFromAccountAssetsWithStockSymbol(List<Asset> assets, String stockSymbol){
        return assets.stream().filter(asset -> asset.getStockSymbol().equals(stockSymbol)).findFirst();
    }

    public Optional<Asset> getAssetFromAccountAssetsWithAssetId(List<Asset> assets, UUID assetId){
        return assets.stream().filter(asset -> asset.getAssetId().equals(assetId)).findFirst();
    }

    @Transactional
    //TODO[] Implement check if Asset is really not already present
    public ResponseEntity<Object> insertNewAsset(UUID accountId, Asset asset){
        Optional<Account> account = accountRepository.findById(accountId);

        System.out.println("AccountManagementServcie.insertNewAsset: 1 ");
        if(account.isPresent()){

            System.out.println("AccountManagementServcie.insertNewAsset: 2 ");
            account.get().addAsset(asset);
            accountRepository.save(account.get());

            System.out.println("AccountManagementServcie.insertNewAsset: 3 ");
            accountRepository.flush();

            System.out.println("AccountManagementServcie.insertNewAsset: 4 ");
            return new ResponseEntity<>("Asset created and added to account ", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Account not found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> addAlreadyPresentAssetToAccount(UUID accountId, Asset asset){
        Optional<Account> account = accountRepository.findById(accountId);
        if(account.isPresent()){
            account.get().addAsset(asset);
            accountRepository.save(account.get());
            accountRepository.flush();
            return new ResponseEntity<>("Asset added to the already present asset account", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Account not found", HttpStatus.NOT_FOUND);
        }
    }

/*
    public ResponseEntity<Object> reduceAssetAmount(UUID accountId, UUID assetId){
        Optional<Account> account = accountRepository.findById(accountId);
        if(account.isPresent()){
            Optional<Asset> asset = getAssetFromAccountAssetsWithAssetName(account.get().getAssets(), assetId.toString());
            if(asset.isPresent()){
                asset.get().setQuantity(asset.get().getQuantity() - 1);
                accountRepository.save(account.get());
                accountRepository.flush();
                return new ResponseEntity<>("Asset reduced", HttpStatus.OK);
            }
        }
    }
*/

    public Boolean isAssetAlreadyPresent(UUID accountId, Asset asset){
        Account account = findAccountEntityById(accountId);
        for(Asset asset1 : account.getAssets()){
            if(asset1.getName().equals(asset.getName())){
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

}
