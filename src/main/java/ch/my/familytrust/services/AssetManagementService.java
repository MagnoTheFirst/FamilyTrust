package ch.my.familytrust.services;

import ch.my.familytrust.dtos.AssetDto;
import ch.my.familytrust.entities.Account;
import ch.my.familytrust.entities.Asset;
import ch.my.familytrust.entities.AssetTransaction;
import ch.my.familytrust.enums.AssetTransactionType;
import ch.my.familytrust.enums.AssetType;
import ch.my.familytrust.repositories.AccountRepository;
import ch.my.familytrust.repositories.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
        System.out.println("AssetManagementService.buyAsset: 1 " + assetDto);
        Optional<Account> account = Optional.ofNullable(accountManagementService.getAccountByAccountId(assetDto.accountId()));
        System.out.println("AssetManagementService.buyAsset: 2 is account empty" + account.isEmpty());
        //TODO[] is assetname really correct or should the id be used? Problem is that I dont know if ID is globaly or per asset assigned to an account
        System.out.println("AssetManagementService.buyAsset: 3 " + assetDto.name() + " " + assetDto.accountId());
        List<Asset> assets = assetRepository.findByAccountId(assetDto.accountId());
        System.out.println("AssetManagementService.buyAsset: 4 is assets empty " + assets.isEmpty() + " !isAssetPresent() " + !isAssetPresent(assetDto.name(), assetDto.accountId()));

        if (assets.isEmpty() || isAssetPresent(assetDto.name(), assetDto.accountId())) {
            Asset newAsset = new Asset(assetDto);

            System.out.println("AssetManagementService.buyAsset: 5 " + assetDto);
            AssetTransaction assetTransaction = new AssetTransaction(AssetTransactionType.STOCK_BUY, assetDto.quantityBigDecimal(), assetDto.currentPrice(), newAsset.getAssetBalance(), assetDto.comment());

            System.out.println("AssetManagementService.buyAsset: 6 " + assetDto);
            newAsset.addAssetTransaction(assetTransaction);
            account.get().getAssets().add(newAsset);
            assetRepository.save(newAsset);
            assetRepository.flush();
            System.out.println("AssetManagementService.buyAsset: 7" + assetDto);
            //TODO[] must be refactored its not really clean

            return accountManagementService.insertNewAsset(assetDto.accountId(), newAsset);
        }
        else{
            Asset asset = assetRepository.findByAssetName(assetDto.name()).orElse(null);
            asset.setCurrentPrice(assetDto.currentPrice());
            asset.setQuantity(asset.getQuantity() + assetDto.quantityBigDecimal());
            AssetTransaction assetTransaction = new AssetTransaction(AssetTransactionType.STOCK_BUY, assetDto.quantityBigDecimal(), assetDto.currentPrice(), asset.getAssetBalance(), assetDto.comment());
            asset.getAssetTransactions().add(assetTransaction);
            assetRepository.save(asset);
            assetRepository.flush();
            return new ResponseEntity<>("Assets added to existing asset portfolio", HttpStatus.OK);
        }
    }



    public ResponseEntity<Object> sellAsset(AssetDto assetDto) {


        Optional<Asset> asset = assetRepository.findByAssetName(assetDto.name());
        if (asset.isEmpty()) {

            return new ResponseEntity<>("Asset not found", HttpStatus.NOT_FOUND);
        }
        //TODO[] implement else logic
        else{
            asset.get().setQuantity(asset.get().getQuantity() - assetDto.quantityBigDecimal());
            asset.get().setCurrentPrice(assetDto.currentPrice());

            asset.get().updateBalance();
            AssetTransaction assetTransaction = new AssetTransaction(AssetTransactionType.STOCK_SELL, assetDto.quantityBigDecimal(), assetDto.currentPrice(), asset.get().getAssetBalance(), assetDto.comment());
            asset.get().addAssetTransaction(assetTransaction);
            BigDecimal stockAmount = new BigDecimal(assetDto.quantityBigDecimal());
            asset.get().setInvestedMoney(assetDto.currentPrice().multiply(stockAmount));
            assetRepository.save(asset.get());
            assetRepository.flush();
            return new ResponseEntity<>("Asset transaction completed", HttpStatus.OK);
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
                asset.getAccount().getId(),
                comment
        );
    }

    public boolean isAssetPresent(String assetName, UUID accountId) {
        return assetRepository.findByAssetNameAndAccountId(assetName,accountId).isEmpty();
    }

}
