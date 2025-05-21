package ch.my.familytrust.entities;

import ch.my.familytrust.enums.AssetTransactionType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
public class AssetTransaction {


    Long assetTransactionId;
    LocalDateTime transactionDate;
    AssetTransactionType assetTransactionType;
    Double amount;
    Double price;
    Double assetTransactionBalance;
    String comment;
    Asset asset;

    AssetTransaction(Asset asset, Double amount, Double price, LocalDateTime transactionDate, AssetTransactionType assetTransactionType){
        //create assetTransaction
        //calculate assetTransactionBalance
        //add assetTransaction to asset
        this.assetTransactionId = UUID.randomUUID();
        this.transactionDate = transactionDate;
        this.assetTransactionType = assetTransactionType;
        this.amount = amount;
        this.price = price;
       // this.asset = modifyAsset(asset, amount, price);
    }

    AssetTransaction(Asset asset, Double amount, Double price, LocalDateTime transactionDate, AssetTransactionType assetTransactionType, String comment){
        this.assetTransactionId = UUID.randomUUID();
        this.transactionDate = transactionDate;
        this.assetTransactionType = assetTransactionType;
        this.amount = amount;
        this.price = price;
        this.comment = comment;
        //modifyAsset(asset, amount, price);
    }

    public Asset modifyAsset(Asset asset, Double amount, Double price, LocalDateTime transactionDate, AssetTransactionType assetTransactionType){

        asset.setAmount(asset.amount + amount);
        asset.setCurrentPrice(price);
        asset.setAssetBalance(asset.amount * asset.currentPrice);
        this.assetTransactionBalance = asset.assetBalance; //TODO[] is this correct?
        //asset.addAssetTransaction(this, );
        return asset;

    }

}
