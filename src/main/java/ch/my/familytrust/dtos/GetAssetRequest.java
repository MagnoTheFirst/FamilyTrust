package ch.my.familytrust.dtos;

import ch.my.familytrust.entities.AssetTransaction;
import ch.my.familytrust.enums.AssetTransactionType;
import ch.my.familytrust.enums.AssetType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record GetAssetRequest(Long assetId,
                              String name,
                              String stockSymbol,
                              AssetType assetType,
                              AssetTransactionType assetTransactionType,
                              String comment,
                              LocalDateTime transactionDate,
                              BigDecimal price,
                              BigDecimal transactionBalance) {
}
