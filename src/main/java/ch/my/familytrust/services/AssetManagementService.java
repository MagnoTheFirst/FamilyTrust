package ch.my.familytrust.services;

import ch.my.familytrust.dtos.AssetDto;
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
    public ResponseEntity<Object> buyAsset(AssetDto assetDto) {
        Optional<Asset> asset = assetRepository.findByAssetName(assetDto.name());
        if (asset.isEmpty()) {
            asset = Optional.of(new Asset(false, true, new BigDecimal(0), assetDto.quantityBigDecimal(), assetDto.currentPrice(), assetDto.assetType(), assetDto.stockSymbol(), assetDto.name()));
            assetRepository.save(asset.get());
            assetRepository.flush();
            return new ResponseEntity<>("Asset successfull bought", HttpStatus.OK);
        }
        else{
            AssetTransaction assetTransaction = new AssetTransaction(AssetTransactionType.STOCK_BUY, assetDto.quantityBigDecimal(), assetDto.currentPrice(), asset.get().getAssetBalance(), assetDto.comment());
            asset.get().setQuantity(asset.get().getQuantity() + assetDto.quantityBigDecimal());
            asset.get().getAssetTransactions().add(assetTransaction);
            asset.get().updateBalance();
            assetRepository.save(asset.get());
            assetRepository.flush();
            return new ResponseEntity<>("Assets added to existing asset portfolio", HttpStatus.OK);
        }
    }


    //TODO[] Implement Sell Asset
    public void sellAsset(AssetDto assetDto) {
        Optional<Asset> asset = assetRepository.findByAssetName(assetDto.name());
        if (asset.isEmpty()) {
            asset = Optional.of(new Asset(false, true, new BigDecimal(0), assetDto.quantityBigDecimal(), assetDto.currentPrice(), assetDto.assetType(), assetDto.stockSymbol(), assetDto.name()));
            assetRepository.save(asset.get());
            assetRepository.flush();
        }
        else{
            AssetTransaction assetTransaction = new AssetTransaction(AssetTransactionType.STOCK_BUY, assetDto.quantityBigDecimal(), assetDto.currentPrice(), asset.get().getAssetBalance(), assetDto.comment());
            asset.get().setQuantity(asset.get().getQuantity() + assetDto.quantityBigDecimal());
            asset.get().getAssetTransactions().add(assetTransaction);
            asset.get().updateBalance();
            assetRepository.save(asset.get());
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
