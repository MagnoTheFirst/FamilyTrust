package ch.my.familytrust.services;

import ch.my.familytrust.dtos.AssetTransactionDto;
import ch.my.familytrust.dtos.GetAssetRequest;
import ch.my.familytrust.entities.Asset;
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
//TODO[] change mapAssetTransactionToDto to mapGetAssetTransactionRequest
@Service
public class AssetTransactionService {

    @Autowired
    AssetManagementService assetManagementService;

    @Autowired
    AssetTransactionRepository assetTransactionRepository;
    @Autowired
    private AccountManagementService accountManagementService;

    public List<GetAssetRequest> old_getAssetTransactions(UUID accountId, Long assetId){
        Optional<AssetTransaction> assetTransactions = assetTransactionRepository.findByAssetTransactionNameAndAccountId(assetId, accountId);
        return assetTransactions.stream()
                .map(this::mapAssetTransactionToDto).collect(Collectors.toList());
    }

    public List<GetAssetRequest> getAssetTransactions(Long assetId){
        List<AssetTransaction> assetTransactions = assetManagementService.getAsset(assetId).getAssetTransactions();
        return assetTransactions.stream()
                .map(this::mapAssetTransactionToDto).collect(Collectors.toList());
    }

    public List<GetAssetRequest> getAssetTransactions(Asset asset){
        List<AssetTransaction> assetTransactions = asset.getAssetTransactions();
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

    public GetAssetRequest mapAssetTransactionToDto(AssetTransaction assetTransaction){
        return new GetAssetRequest(
                assetTransaction.getAssetTransactionId(),
                assetTransaction.getAsset().getName(),
                assetTransaction.getAsset().getStockSymbol(),
                assetTransaction.getAsset().getAssetType(),
                assetTransaction.getAssetTransactionType(),
                assetTransaction.getComment(),
                assetTransaction.getTransactionDate(),
                assetTransaction.getPrice(),
                assetTransaction.getAssetTransactionBalance());
    }
}
