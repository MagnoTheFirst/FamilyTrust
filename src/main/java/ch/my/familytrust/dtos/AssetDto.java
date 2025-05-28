package ch.my.familytrust.dtos;

import ch.my.familytrust.entities.Account;
import ch.my.familytrust.entities.AssetTransaction;
import ch.my.familytrust.enums.AssetTransactionType;
import ch.my.familytrust.enums.AssetType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record AssetDto(Long assetId, String name, String stockSymbol, AssetType assetType, AssetTransactionType assetTransactionType, BigDecimal currentPrice, Double quantityBigDecimal, UUID accountId,
                       String comment) {
}
