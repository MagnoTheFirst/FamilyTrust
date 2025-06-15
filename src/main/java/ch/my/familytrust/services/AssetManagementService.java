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
        return assetRepository.findByAccountId(uuid).stream() // Annahme: getAccountCashFlows() ist der Getter in Ihrer Account-Entität
                .map(this::mapAssetToAssetDto).collect(Collectors.toList());
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
        if(assetDto.assetTransactionType().equals(AssetTransactionType.STOCK_BUY)) {
            return buyAsset(assetDto);
        } else if (assetDto.assetTransactionType().equals(AssetTransactionType.STOCK_SELL)) {
            return sellAsset(assetDto);
        } else if (assetDto.assetTransactionType().equals(AssetTransactionType.PHYSICAL_ASSET_BUY)) {
            return null;
        } else if (assetDto.assetTransactionType().equals(AssetTransactionType.PHYSICAL_ASSET_SELL)) {
            return null;
        } else if (assetDto.assetTransactionType().equals(AssetTransactionType.CRYPTO_CURRENCY_BUY)) {
            return null;
        } else if (assetDto.assetTransactionType().equals(AssetTransactionType.PHYSICAL_ASSET_SELL)) {
            return null;
        } else if (assetDto.assetTransactionType().equals(AssetTransactionType.ETF_BUY)) {
            return null;
        } else if (assetDto.assetTransactionType().equals(AssetTransactionType.ETF_SELL)) {
            return null;
        } else{
            return new ResponseEntity<>("UNKNOWN TRANSACTION",HttpStatus.BAD_REQUEST);
        }
    }


    //TODO[] Test this method
    public ResponseEntity<Object> buyAsset(AssetDto assetDto) {

        Optional<Account> account = Optional.ofNullable(accountManagementService.getAccountByAccountId(assetDto.accountId()));


        List<Asset> assets = assetRepository.findByAccountId(assetDto.accountId());
        BigDecimal investmentAmount = new BigDecimal(String.valueOf(assetDto.quantityBigDecimal())).multiply(assetDto.currentPrice());
        System.out.println(account.get().getAvailableMoney().compareTo(investmentAmount));
        if(account.get().getAvailableMoney().compareTo(investmentAmount) == -1) {
            return new ResponseEntity<>("AVAILABLE MONEY NOT SUFFICIENT",HttpStatus.EXPECTATION_FAILED);
        }
        if (assets.isEmpty() || isAssetPresent(assetDto.name(), assetDto.accountId())) {
            Asset newAsset = new Asset(assetDto);

            AssetTransaction assetTransaction = new AssetTransaction(newAsset, AssetTransactionType.STOCK_BUY, assetDto.quantityBigDecimal(), assetDto.currentPrice(), newAsset.getAssetBalance(), assetDto.comment());

            newAsset.addAssetTransaction(assetTransaction);
            account.get().getAssets().add(newAsset);
            newAsset.setAssetBalance(newAsset.getAssetBalance());
            assetRepository.save(newAsset);
            assetRepository.flush();

            //TODO[] must be refactored its not really clean

            return accountManagementService.insertNewAsset(assetDto.accountId(), newAsset);
        }
        else{
            Asset asset = assetRepository.findByAssetNameAndAccountId(assetDto.name(), assetDto.accountId()).orElse(null);
            System.out.println(asset.toString());
            System.out.println(assetTransactionRepository.findAll());
            asset.setCurrentPrice(assetDto.currentPrice());
            asset.setQuantity(asset.getQuantity() + assetDto.quantityBigDecimal());
            AssetTransaction assetTransaction = new AssetTransaction(asset, AssetTransactionType.STOCK_BUY, assetDto.quantityBigDecimal(), assetDto.currentPrice(), asset.getAssetBalance(), assetDto.comment());
            asset.getAssetTransactions().add(assetTransaction);
            asset.setAssetBalance(asset.getAssetBalance());
            assetRepository.save(asset);
            assetRepository.flush();
            return new ResponseEntity<>("Assets added to existing asset portfolio", HttpStatus.OK);
        }
    }



    public ResponseEntity<Object> sellAsset(AssetDto assetDto) {

        List<Asset> assets = assetRepository.findByAccountId(assetDto.accountId());
        Account account = accountManagementService.getAccountByAccountId(assetDto.accountId());
        if (assets.isEmpty() || isAssetPresent(assetDto.name(), assetDto.accountId())) {
            return new ResponseEntity<>("Asset not present in current portfolio", HttpStatus.BAD_REQUEST);
        }
        else{
            Asset asset = assetRepository.findByAssetNameAndAccountId(assetDto.name(), assetDto.accountId()).orElse(null);
            asset.setCurrentPrice(assetDto.currentPrice());
            asset.setQuantity(asset.getQuantity() - assetDto.quantityBigDecimal());
            AssetTransaction assetTransaction = new AssetTransaction(asset, AssetTransactionType.STOCK_SELL, (assetDto.quantityBigDecimal()*-1), assetDto.currentPrice(), asset.getAssetBalance(), assetDto.comment());
            asset.getAssetTransactions().add(assetTransaction);
            asset.setAssetBalance(asset.getAssetBalance());
            account.getAvailableMoney().add(assetTransaction.getAssetTransactionBalance());
            //            BigDecimal realizedProfitLoss = new BigDecimal(assetDto.quantityBigDecimal());
//            realizedProfitLoss.add(realizedProfitLoss.multiply(assetDto.currentPrice()));
//            asset.getRealizedProfitLoss().add(realizedProfitLoss);
            assetRepository.save(asset);
            assetRepository.flush();
            return new ResponseEntity<>(assetDto.quantityBigDecimal() + " Stocks of " + assetDto.name() + " sold.", HttpStatus.OK);
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
        return assetRepository.findByAssetNameAndAccountId(assetName,accountId).isEmpty();
    }

    public Asset getAssetByAssetNameAndAccountId(String assetName, UUID accountId) {
        return assetRepository.findByAssetNameAndAccountId(assetName, accountId).orElse(null);
    }
}
