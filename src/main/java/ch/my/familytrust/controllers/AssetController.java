package ch.my.familytrust.controllers;

import ch.my.familytrust.dtos.AssetDto;
import ch.my.familytrust.enums.AssetTransactionType;
import ch.my.familytrust.services.AccountManagementService;
import ch.my.familytrust.services.AssetManagementService;
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

    public AssetController(AssetManagementService assetManagementService) {
        this.assetManagementService = assetManagementService;
    }


    @GetMapping("/user/{user-id}/account/{account-id}/asset/{asset-id}")
    public ResponseEntity<Object> getAsset(@PathVariable("user-id") UUID userId, @PathVariable("account-id") UUID accountId, @PathVariable("asset-id") Long assetId){
        return new ResponseEntity<>("ASSET", HttpStatus.OK);
    }

    @GetMapping("/user/{user-id}/account/{account-id}/list/assets")
    public ResponseEntity<Object> getAssets(@PathVariable("user-id") UUID userId, @PathVariable("account-id") UUID accountId){
        return new ResponseEntity<>("ASSET", HttpStatus.OK);
    }

    @PostMapping("/user/{user-id}/account/{account-id}/asset-transaction")
    public ResponseEntity<Object> buyAsset(@PathVariable("user-id") UUID userId, @PathVariable("account-id") UUID accountId){
        return new ResponseEntity<>("BUY ASSET", HttpStatus.OK);
    }

    @PostMapping("/asset-transaction")
    public ResponseEntity<Object> buyAsset(AssetDto assetDto) {
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
