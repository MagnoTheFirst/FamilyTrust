package ch.my.familytrust.controllers;

import ch.my.familytrust.dtos.AssetDto;
import ch.my.familytrust.entities.Asset;
import ch.my.familytrust.enums.AssetTransactionType;
import ch.my.familytrust.enums.AssetType;
import ch.my.familytrust.services.AccountManagementService;
import ch.my.familytrust.services.AssetManagementService;
import ch.my.familytrust.services.AssetTransactionService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 *
 * This controller will be implemented further on.
 * It is ment to list all Stocks with its corresponding prices.
 * */

@RestController("/api/v1/asset-controller")
public class AssetController {

    @Autowired
    AssetManagementService assetManagementService;

    @Autowired
    AccountManagementService accountManagementService;

    @Autowired
    AssetTransactionService assetTransactionService;

    public AssetController(AssetManagementService assetManagementService) {
        this.assetManagementService = assetManagementService;
    }


    @GetMapping("/user/{user-id}/account/{account-id}/asset/{asset-id}")
    public ResponseEntity<Object> getAsset(@PathVariable("user-id") UUID userId, @PathVariable("account-id") UUID accountId, @PathVariable("asset-id") Long assetId){
        return new ResponseEntity<>("ASSET", HttpStatus.OK);
    }

    @GetMapping("/user/{user-id}/account/{account-id}/list/assets")
    public ResponseEntity<Object> getAssets(@PathVariable("user-id") UUID userId, @PathVariable("account-id") UUID accountId){
        return new ResponseEntity<>(assetManagementService.getAssets(accountId), HttpStatus.OK);
    }
/*
    //TODO[] Prototype cleancode if it works
    @GetMapping("/user/{user-id}/account/{account-id}/list/assetTransactions/{asset-id}")
    public ResponseEntity<Object> getAssetTransactionList(@PathVariable("user-id") UUID userId, @PathVariable("account-id") UUID accountId, @PathVariable("asset-id") Long assetId){
        //Asset asset = assetManagementService.getAsset(userId);
        //System.out.println(asset.getAssetTransactions().isEmpty());
        return new ResponseEntity<>(assetTransactionService.getAssetTransactions(asset), HttpStatus.OK);
    }*/

    //TODO[] Prototype cleancode if it works
    @GetMapping("/account/{account-id}/list/assetTransactions/{asset-name}")
    public ResponseEntity<Object> getAssetTransactionList(@PathVariable("account-id") UUID accountId, @PathVariable("asset-name") String assetName){
        Asset asset = assetManagementService.getAssetByAssetNameAndAccountId(assetName, accountId);
        System.out.println(asset.getAssetTransactions().isEmpty());
        return new ResponseEntity<>(assetTransactionService.getAssetTransactions(asset), HttpStatus.OK);
    }

    @GetMapping("/user/{user-id}/account/{account-id}/list/asset-types/{asset-type}")
    public ResponseEntity<Object> listSpecificAssetTypes(@PathVariable("user-id") UUID userId, @PathVariable("account-id") UUID accountId, @PathVariable("asset-type") AssetType assetType){
        return new ResponseEntity<>(assetManagementService.getSpecificAssetType(assetType), HttpStatus.OK);
    }

    @PostMapping("/asset-transaction")
    public ResponseEntity<Object> buyOrSellAsset(AssetDto assetDto) {
        if(assetDto.assetTransactionType().equals(AssetTransactionType.STOCK_BUY)){
            return assetManagementService.buyAsset(assetDto);
        }
        else if(assetDto.assetTransactionType().equals(AssetTransactionType.STOCK_SELL)){
            return assetManagementService.sellAsset(assetDto);
        }
        else{
            return new ResponseEntity<>("TRANSACTION FAILED", HttpStatus.BAD_REQUEST);
        }
    }

}
