package ch.my.familytrust.services;

import ch.my.familytrust.dtos.AssetDto;
import ch.my.familytrust.entities.Account;
import ch.my.familytrust.entities.Asset;
import ch.my.familytrust.entities.AssetTransaction;
import ch.my.familytrust.enums.AssetTransactionType;
import ch.my.familytrust.enums.AssetType;
import ch.my.familytrust.repositories.AccountRepository;
import ch.my.familytrust.repositories.AssetRepository;
import ch.my.familytrust.repositories.AssetTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Dictionary;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


//TODO[] REMOVE CASH AS ASSET TYPE
@Service
public class AssetManagementService {

    @Autowired
    AssetRepository assetRepository;

    @Autowired
    AccountManagementService accountManagementService;
    @Autowired
    private AssetTransactionRepository assetTransactionRepository;

    public AssetManagementService(AccountRepository accountRepository, AccountManagementService accountManagementService) {
        this.accountManagementService = accountManagementService;
        this.assetRepository = assetRepository;
    }


    public List<AssetDto> getAssets(UUID uuid) {
        Account account = accountManagementService.getAccountByAccountId(uuid);
        List<Asset> assets = assetRepository.findActiveByAccountId(uuid);
        
        return assets.stream()
                .map(this::mapAssetToAssetDto)
                .collect(Collectors.toList());
    }

    public List<AssetDto> getSpecificAssetType(AssetType assetType) {
        return assetRepository.findAssetByAssetType(assetType).stream() // Annahme: getAccountCashFlows() ist der Getter in Ihrer Account-Entität
                .map(this::mapAssetToAssetDto).collect(Collectors.toList());
    }

    public List<Asset> getAssetsByUserId(UUID uuid) {
        Account account = accountManagementService.getAccountByAccountId(uuid);
        return assetRepository.findByAccountId(uuid); //accountManagementService.getAssetsFromAccount(account);
    }

    public Asset getAsset(Long uuid) {

        return assetRepository.findById(uuid).orElse(null);
    }

    public AssetDto getAssetDtoByAssetId(Long uuid) {
        Asset asset = getAsset(uuid);
        return mapAssetToAssetDto(asset, AssetTransactionType.STOCK_BUY);
    }

    public ResponseEntity<Object> newAssetTransaction(AssetDto assetDto) {
        // Handle BUY transactions for all asset types
        if(assetDto.assetTransactionType().equals(AssetTransactionType.STOCK_BUY) ||
           assetDto.assetTransactionType().equals(AssetTransactionType.ETF_BUY) ||
           assetDto.assetTransactionType().equals(AssetTransactionType.CRYPTO_CURRENCY_BUY) ||
           assetDto.assetTransactionType().equals(AssetTransactionType.PHYSICAL_ASSET_BUY)) {
            return buyAsset(assetDto);
        }
        // Handle SELL transactions for all asset types
        else if (assetDto.assetTransactionType().equals(AssetTransactionType.STOCK_SELL) ||
                 assetDto.assetTransactionType().equals(AssetTransactionType.ETF_SELL) ||
                 assetDto.assetTransactionType().equals(AssetTransactionType.CRYPTO_CURRENCY_SELL) ||
                 assetDto.assetTransactionType().equals(AssetTransactionType.PHYSICAL_ASSET_SELL)) {
            return sellAsset(assetDto);
        } else{
            return new ResponseEntity<>("UNKNOWN TRANSACTION",HttpStatus.BAD_REQUEST);
        }
    }


    //TODO[] Test this method
    public ResponseEntity<Object> buyAsset(AssetDto assetDto) {

        Optional<Account> account = Optional.ofNullable(accountManagementService.getAccountByAccountId(assetDto.accountId()));


        BigDecimal investmentAmount = new BigDecimal(String.valueOf(assetDto.quantityBigDecimal())).multiply(assetDto.currentPrice());
        if(account.get().getAvailableMoney().compareTo(investmentAmount) == -1) {
            return new ResponseEntity<>("AVAILABLE MONEY NOT SUFFICIENT",HttpStatus.EXPECTATION_FAILED);
        }
        if (!isAssetPresent(assetDto.name(), assetDto.accountId())) {
            // Create new asset
            Asset newAsset = new Asset(assetDto);
            newAsset.setAccount(account.get());
            // Ensure active field is explicitly set
            newAsset.setActive(true);
            newAsset.setArchived(false);

            AssetTransaction assetTransaction = new AssetTransaction(newAsset, assetDto.assetTransactionType(), assetDto.quantityBigDecimal(), assetDto.currentPrice(), investmentAmount, assetDto.comment());

            newAsset.addAssetTransaction(assetTransaction);
            newAsset.updateBalance();
            
            // Update account balance
            BigDecimal newBalance = account.get().getAvailableMoney().subtract(investmentAmount);
            account.get().setAvailableMoney(newBalance);
            
            // Save asset first, then update account
            assetRepository.save(newAsset);
            account.get().getAssets().add(newAsset);
            accountManagementService.updateAccount(account.get());

            return new ResponseEntity<>("New asset '" + assetDto.name() + "' created and added to portfolio with quantity " + assetDto.quantityBigDecimal(), HttpStatus.OK);
        }
        else{
            Asset asset = assetRepository.findActiveByAssetNameAndAccountId(assetDto.name(), assetDto.accountId()).orElse(null);
            asset.setCurrentPrice(assetDto.currentPrice());
            asset.setQuantity(asset.getQuantity() + assetDto.quantityBigDecimal());
            AssetTransaction assetTransaction = new AssetTransaction(asset, assetDto.assetTransactionType(), assetDto.quantityBigDecimal(), assetDto.currentPrice(), investmentAmount, assetDto.comment());
            asset.getAssetTransactions().add(assetTransaction);
            asset.updateBalance();
            
            // Update account balance for existing asset
            BigDecimal newBalance = account.get().getAvailableMoney().subtract(investmentAmount);
            account.get().setAvailableMoney(newBalance);
            accountManagementService.updateAccount(account.get());
            
            assetRepository.save(asset);
            assetRepository.flush();
            return new ResponseEntity<>("Assets added to existing asset portfolio", HttpStatus.OK);
        }
    }



    public ResponseEntity<Object> sellAsset(AssetDto assetDto) {
        try {
            // Validierung der Account-Existenz
            Account account = accountManagementService.getAccountByAccountId(assetDto.accountId());
            if (account == null) {
                return new ResponseEntity<>("Account not found", HttpStatus.NOT_FOUND);
            }

            // Validierung der Asset-Existenz
            Optional<Asset> assetOpt = assetRepository.findActiveByAssetNameAndAccountId(assetDto.name(), assetDto.accountId());
            if (assetOpt.isEmpty()) {
                return new ResponseEntity<>("Asset not present in current portfolio", HttpStatus.BAD_REQUEST);
            }

            Asset asset = assetOpt.get();
            
            // Validierung der verfügbaren Menge
            if (asset.getQuantity() < assetDto.quantityBigDecimal()) {
                return new ResponseEntity<>("Insufficient asset quantity. Available: " + asset.getQuantity() + ", Requested: " + assetDto.quantityBigDecimal(), HttpStatus.BAD_REQUEST);
            }

            // Verkaufswert berechnen
            BigDecimal sellValue = assetDto.currentPrice().multiply(BigDecimal.valueOf(assetDto.quantityBigDecimal()));
            
            // Asset-Menge reduzieren
            double newQuantity = asset.getQuantity() - assetDto.quantityBigDecimal();
            asset.setQuantity(newQuantity);
            asset.setCurrentPrice(assetDto.currentPrice());

            // Verkaufs-Transaktion erstellen
            AssetTransaction sellTransaction = new AssetTransaction(
                asset, 
                assetDto.assetTransactionType(), 
                assetDto.quantityBigDecimal(), 
                assetDto.currentPrice(), 
                sellValue,
                assetDto.comment() != null ? assetDto.comment() : "Asset sale"
            );
            
            asset.getAssetTransactions().add(sellTransaction);
            
            // Account-Balance aktualisieren
            BigDecimal newAccountBalance = account.getAvailableMoney().add(sellValue);
            account.setAvailableMoney(newAccountBalance);
            
            // Asset-Balance neu berechnen
            asset.updateBalance();
            
            // Speichern
            assetRepository.save(asset);
            accountManagementService.updateAccount(account);
            
            // Bei vollständigem Verkauf Asset aus Account entfernen aber in DB belassen für Transaktionshistorie
            if (newQuantity == 0) {
                asset.setActive(false);
                assetRepository.save(asset);
                
                // Asset aus Account-Liste entfernen
                account.getAssets().removeIf(a -> a.getAssetId().equals(asset.getAssetId()));
                accountManagementService.updateAccount(account);
                
                return new ResponseEntity<>("All " + assetDto.quantityBigDecimal() + " shares of " + assetDto.name() + " sold completely. Asset marked as inactive.", HttpStatus.OK);
            }
            
            return new ResponseEntity<>(assetDto.quantityBigDecimal() + " shares of " + assetDto.name() + " sold for " + sellValue + ". Remaining quantity: " + newQuantity, HttpStatus.OK);
            
        } catch (Exception e) {
            return new ResponseEntity<>("Error selling asset: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public AssetDto mapAssetToAssetDto(Asset asset, AssetTransactionType assetTransactionType) {
        return new AssetDto(
                asset.getAssetId(),
                asset.getName(),
                asset.getStockSymbol(),
                asset.getAssetType(),
                assetTransactionType,
                asset.getCurrentPrice(),
                asset.getQuantity(),
                asset.getAssetBalance(),
                asset.getAccount().getId(),
                ""
        );
    }

    public AssetDto mapAssetToAssetDto(Asset asset) {
        return new AssetDto(
                asset.getAssetId(),
                asset.getName(),
                asset.getStockSymbol(),
                asset.getAssetType(),
                AssetTransactionType.STOCK_BUY,
                asset.getCurrentPrice(),
                asset.getQuantity(),
                asset.getAssetBalance(),
                asset.getAccount().getId(),
                ""
        );
    }

    public AssetDto mapAssetToAssetDto(Asset asset, AssetTransactionType assetTransactionType, String comment) {
        return new AssetDto(
                asset.getAssetId(),
                asset.getName(),
                asset.getStockSymbol(),
                asset.getAssetType(),
                assetTransactionType,
                asset.getCurrentPrice(),
                asset.getQuantity(),
                asset.getAssetBalance(),
                asset.getAccount().getId(),
                comment
        );
    }

    public boolean isAssetPresent(String assetName, UUID accountId) {
        return assetRepository.findActiveByAssetNameAndAccountId(assetName, accountId).isPresent();
    }

    public Asset getAssetByAssetNameAndAccountId(String assetName, UUID accountId) {
        return assetRepository.findByAssetNameAndAccountId(assetName, accountId).orElse(null);
    }
}
