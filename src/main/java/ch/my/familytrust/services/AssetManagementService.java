package ch.my.familytrust.services;

import ch.my.familytrust.dtos.AssetDto;
import ch.my.familytrust.entities.Account;
import ch.my.familytrust.entities.Asset;
import ch.my.familytrust.entities.AssetTransaction;
import ch.my.familytrust.enums.AssetTransactionType;
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


    public List<Asset> getAssets(UUID uuid) {
        Account account = accountManagementService.getAccountByAccountId(uuid);
        return assetRepository.findAll(); //accountManagementService.getAssetsFromAccount(account);
    }

    public Asset getAsset(Long uuid) {

        return assetRepository.findById(uuid).orElse(null);
    }

    public AssetDto getAssetDto(Long uuid) {
        Asset asset = getAsset(uuid);
        return mapAssetToAssetDto(asset, AssetTransactionType.STOCK_BUY);
    }


    //TODO[] Test this method
    public ResponseEntity<Object> buyAsset(AssetDto assetDto) {
        System.out.println("AssetManagementService.buyAsset: 1 " + assetDto);
        Optional<Account> account = Optional.ofNullable(accountManagementService.getAccountByAccountId(assetDto.accountId()));
        System.out.println("AssetManagementService.buyAsset: 2 " + assetDto);
        //TODO[] is assetname really correct or should the id be used? Problem is that I dont know if ID is globaly or per asset assigned to an account
        Optional<Asset> asset = accountManagementService.getAssetFromAccountAssetsWithAssetName(account.get().getAssets(), assetDto.name());

        System.out.println("AssetManagementService.buyAsset: 3 " + assetDto);
        if (asset.isEmpty()) {
            Asset newAsset = new Asset(assetDto);

            System.out.println("AssetManagementService.buyAsset: 4 " + assetDto);
            AssetTransaction assetTransaction = new AssetTransaction(AssetTransactionType.STOCK_BUY, assetDto.quantityBigDecimal(), assetDto.currentPrice(), newAsset.getAssetBalance(), assetDto.comment());

            System.out.println("AssetManagementService.buyAsset: 5 " + assetDto);
            newAsset.addAssetTransaction(assetTransaction);
            account.get().getAssets().add(newAsset);

            System.out.println("AssetManagementService.buyAsset: 6" + assetDto);
            //TODO[] must be refactored its not really clean

            return accountManagementService.insertNewAsset(assetDto.accountId(), newAsset);
        }
        //TODO[] implement else logic
        else{

            return new ResponseEntity<>("Assets added to existing asset portfolio", HttpStatus.OK);
        }
    }



    public ResponseEntity<Object> sellAsset(AssetDto assetDto) {

        Optional<Account> account = Optional.ofNullable(accountManagementService.getAccountByAccountId(assetDto.accountId()));
        Optional<Asset> asset = assetRepository.findByAssetName(assetDto.name());
        if (asset.isEmpty()) {
            Asset newAsset = new Asset(assetDto);
            AssetTransaction assetTransaction = new AssetTransaction(AssetTransactionType.STOCK_BUY, assetDto.quantityBigDecimal(), assetDto.currentPrice(), newAsset.getAssetBalance(), assetDto.comment());
            newAsset.addAssetTransaction(assetTransaction);
            accountManagementService.insertNewAsset(account.get().getId(), newAsset);
            return new ResponseEntity<>("Asset successfull bought", HttpStatus.OK);
        }
        //TODO[] implement else logic
        else{
            AssetTransaction assetTransaction = new AssetTransaction(AssetTransactionType.STOCK_BUY, assetDto.quantityBigDecimal(), assetDto.currentPrice(), asset.get().getAssetBalance(), assetDto.comment());
            asset.get().setQuantity(asset.get().getQuantity() + assetDto.quantityBigDecimal());
            asset.get().setCurrentPrice(assetDto.currentPrice());
            asset.get().addAssetTransaction(assetTransaction);
            asset.get().updateBalance();
            BigDecimal stockAmount = new BigDecimal(assetDto.quantityBigDecimal());
            asset.get().setInvestedMoney(assetDto.currentPrice().multiply(stockAmount));
            assetRepository.save(asset.get());
            assetRepository.flush();
            return new ResponseEntity<>("Assets added to existing asset portfolio", HttpStatus.OK);
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

    public boolean checkIfAssetAlreadyExist(String assetName, UUID accountId) {
        return assetRepository.findByAssetNameAndAccountId(assetName,accountId).isPresent();
    }

}
