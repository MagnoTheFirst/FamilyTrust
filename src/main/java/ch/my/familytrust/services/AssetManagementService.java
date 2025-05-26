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
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class AssetManagementService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AssetRepository assetRepository;


    public AssetManagementService(AccountRepository accountRepository, AssetRepository assetRepository) {
        this.accountRepository = accountRepository;
        this.assetRepository = assetRepository;
    }

    public List<Asset> getAssets() {
        return assetRepository.findAll();
    }

    public Asset getAsset(Long uuid) {
        return assetRepository.findById(uuid).orElse(null);
    }

    public AssetDto getAssetDto(Long uuid) {
        Asset asset = getAsset(uuid);
        return mapAssetToAssetDto(asset, AssetTransactionType.STOCK_BUY);
    }


    //TODO[] Test this method
    public void buyAsset(AssetDto assetDto) {
        Asset asset = assetRepository.findByAssetName(assetDto.name());
        if (asset == null) {
            asset = new Asset(false, true, new BigDecimal(0), assetDto.quantityBigDecimal(), assetDto.currentPrice(), assetDto.assetType(), assetDto.stockSymbol(), assetDto.name());
            assetRepository.save(asset);
            assetRepository.flush();
        }
        else{
            AssetTransaction assetTransaction = new AssetTransaction(AssetTransactionType.STOCK_BUY, assetDto.quantityBigDecimal(), assetDto.currentPrice(), asset.getAssetBalance(), assetDto.comment());
            asset.setQuantity(asset.getQuantity() + assetDto.quantityBigDecimal());
            asset.getAssetTransactions().add(assetTransaction);
            asset.updateBalance();
            assetRepository.save(asset);
            assetRepository.flush();
        }

    }


    //TODO[] Implement Sell Asset
    public void sellAsset(AssetDto assetDto) {
        Asset asset = assetRepository.findByAssetName(assetDto.name());
        if (asset == null) {
            asset = new Asset(false, true, new BigDecimal(0), assetDto.quantityBigDecimal(), assetDto.currentPrice(), assetDto.assetType(), assetDto.stockSymbol(), assetDto.name());
            assetRepository.save(asset);
            assetRepository.flush();
        }
        else{
            AssetTransaction assetTransaction = new AssetTransaction(AssetTransactionType.STOCK_BUY, assetDto.quantityBigDecimal(), assetDto.currentPrice(), asset.getAssetBalance(), assetDto.comment());
            asset.setQuantity(asset.getQuantity() + assetDto.quantityBigDecimal());
            asset.getAssetTransactions().add(assetTransaction);
            asset.updateBalance();
            assetRepository.save(asset);
            assetRepository.flush();
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


}
