package ch.my.familytrust.services;

import ch.my.familytrust.dtos.AssetTransactionDto;
import ch.my.familytrust.entities.AssetTransaction;
import ch.my.familytrust.enums.AssetTransactionType;
import ch.my.familytrust.repositories.AssetRepository;
import ch.my.familytrust.repositories.AssetTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AssetTransactionService {

    @Autowired
    AssetManagementService assetManagementService;

    @Autowired
    AssetTransactionRepository assetTransactionRepository;

    public List<AssetTransactionDto> getAssetTransactions(UUID accountId, Long assetId){
        Optional<AssetTransaction> assetTransactions = assetTransactionRepository.findByAssetTransactionNameAndAccountId(assetId, accountId);
        return assetTransactions.stream()
                .map(this::mapAssetTransactionToDto).collect(Collectors.toList());
    }


    public BigDecimal getAssetTransactionBlance(Long assetId){
        List<AssetTransaction> assetTransactions = assetManagementService.getAsset(assetId).getAssetTransactions();
        BigDecimal balance = BigDecimal.ZERO;
        for (AssetTransaction assetTransaction : assetTransactions) {
            BigDecimal tmp = new BigDecimal(assetTransaction.getQuantity());
            tmp= tmp.multiply(assetTransaction.getPrice());
            balance = balance.add(tmp);
        }
        return balance;
    }

    public AssetTransactionDto mapAssetTransactionToDto(AssetTransaction assetTransaction){
        return new AssetTransactionDto(assetTransaction.getAssetTransactionId(),
                assetTransaction.getTransactionDate(),
                assetTransaction.getAssetTransactionType(),
                assetTransaction.getQuantity(),
                assetTransaction.getPrice(),
                assetTransaction.getAssetTransactionBalance(),
                assetTransaction.getComment(), assetTransaction.getAsset().getName());
    }
}
